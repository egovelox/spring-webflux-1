package org.exo.hreactor1.config;

import java.util.Optional;

import io.micrometer.context.ContextRegistry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@Component
public class MDCFilter implements WebFilter {
  private final String CORRELATION_ID_KEY = "correlationId";
  private final String CORRELATION_ID_DEFAULT = "unknown";

  @Override
  public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
    final var request = exchange.getRequest();
    final Optional<String> correlationId = extractCorrelationId(request.getHeaders());

    ContextRegistry.getInstance().registerThreadLocalAccessor(
      CORRELATION_ID_KEY,
      () -> MDC.get(CORRELATION_ID_KEY),
      value -> MDC.put(CORRELATION_ID_KEY, value),
      () -> MDC.remove(CORRELATION_ID_KEY)
    );

    return chain.filter(exchange)
      .contextWrite(Context.of(CORRELATION_ID_KEY, correlationId.orElse(CORRELATION_ID_DEFAULT)));
  }

  private Optional<String> extractCorrelationId(final HttpHeaders headers) {
    /* Header key should have a leading capitalized character */
    final String id = headers.getFirst(StringUtils.capitalize(CORRELATION_ID_KEY));
    return Optional.ofNullable(id);
  }
}
