package com.hello;

import com.hello.catalog.CatalogClient;
import com.hello.catalog.DemoCounters;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {


  @Bean
  MyInterface myInterface(){
  return new MyInterfaceImpl();
  }


  @Bean
  CatalogClient catalogClient(DemoCounters demoCounters) {
    return CatalogClient.from(demoCounters);
  }

  @Bean
  DemoCounters metrics(MeterRegistry meterRegistry){
    return DemoCounters.init(meterRegistry);
  }

  @Bean
  Counter counter(MeterRegistry registry) {
    return Counter.builder("request_count")
        .description("Number of hello requests.")
        .register(registry);
  }



}