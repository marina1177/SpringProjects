package BeanDI;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import org.springframework.context.annotation.Scope;

@Configuration
public class MetricsConfiguration {

  @Bean
  public CollectorRegistry collectorRegistry() {
    return new CollectorRegistry(true);
  }

  @Bean
  Gauge gauge() {
    return Gauge.build()
        .name("request_gauge")
        .help("card upload rate")
        .register(collectorRegistry());
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  Counter counter() {
    return Counter.build()
        .name("request_count")
        .help("request counter")
        .register(collectorRegistry());
  }
}