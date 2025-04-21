package org.exo.hreactor1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import reactor.core.publisher.Hooks;

@SpringBootApplication(
    scanBasePackages = { "org.exo" }, exclude = { DataSourceAutoConfiguration.class }
)
public class Application {

  public static void main(String[] args) {
    /* micrometer context-propagation, see config/MDCFilter.java */
    Hooks.enableAutomaticContextPropagation();
    SpringApplication.run(org.exo.hreactor1.Application.class, args);
  }
}
