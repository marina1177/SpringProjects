package com.hello;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

@Bean
MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
  return registry -> {
    registry.config().commonTags("FStoKafka", "mdobro_metrics_service");
  };
}

  @Bean
  Counter counter(MeterRegistry registry){
    return Counter.builder("request_count")
        .description("Number of hello requests.")
        .register(registry);
  }

}