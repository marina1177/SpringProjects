package com.hello;

import com.hello.catalog.CatalogClient;
import com.hello.catalog.DemoCounters;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import javax.annotation.PostConstruct;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

  @PostConstruct
  MeterRegistryCustomizer<MeterRegistry> meterRegistryMeterRegistryCustomizer() {
    return MeterRegistry::config;
  }

  @Bean
  CatalogClient catalogClient(MeterRegistry meterRegistry) {
    return CatalogClient.from(DemoCounters.init(meterRegistry));
  }

  @Bean
  Counter counter(MeterRegistry registry) {
    return Counter.builder("request_count")
        .description("Number of hello requests.")
        .register(registry);
  }

}