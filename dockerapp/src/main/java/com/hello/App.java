
package com.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.metrics.export.prometheus.EnablePrometheusMetrics;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnablePrometheusMetrics
//@EnableSpringBootMetricsCollector
@SpringBootApplication
@EnableScheduling
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
