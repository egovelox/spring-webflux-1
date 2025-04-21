package org.exo.hreactor1.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("${v1.baseUrl}")
@Log4j2
public class ContextController {

  @GetMapping(value = "/context", produces = { APPLICATION_JSON_VALUE })
  public Mono<String> handleUserContextRequest(@RequestHeader("Authorization") String authHeader) {
    /* check that correlationId is correctly configured */
    log.info("Received request on endpoint: /context");
    return Mono.just("{\"context\":\"ok\"}");
  }
}
