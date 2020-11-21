package parser.config;

import io.micrometer.core.instrument.Counter;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.micrometer.core.instrument.MeterRegistry;

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
  return Counter.builder("fs.counter")
        .tag("fs.calls", "load")
        .description("The successful message count")
        .register(registry);
  }
}
