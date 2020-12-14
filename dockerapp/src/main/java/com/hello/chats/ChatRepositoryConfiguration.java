package com.hello.chats;


import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ChatRepositoryConfiguration {


  @Bean
  ChatRepository chatRepository() {

    return new ChatRepositoryImpl();
  }
//      @Bean
//    MeterBinder chatMetrics(ChatRepository chatRepository) {
//
//        return Gauge.builder("search.chat.gauge.upload.cards",
//                () -> chatRepository.count().doOnNext(res -> {
//                    log.info("BEAN CHECK TIME: {}\ncount: {}", System.currentTimeMillis(), res);
//                })
//                        .block(Duration.ofMillis(50)))
//                ::register;
//    }

  @Bean
  MeterBinder chatMetrics(ChatRepository chatRepository) {
    final AtomicLong atomicLong = new AtomicLong();
    return registry -> Gauge.builder(
        "search.chat.gauge.upload.cards",
        atomicLong,
        container -> {
          chatRepository.count().subscribe(v -> container.set(v.intValue()));
          log.info("BEAN count: {}", container.get());
          return container.doubleValue();
        })
        .register(registry);
  }


//  @Bean
//  Disposable temp(MeterRegistry registry, ChatRepository chatRepository) {
//    Mono<Integer> mono = chatRepository.count();)
//    return mono.subscribe();
//  }
//
//  @Bean
//  public Gauge chatGauge(MeterRegistry registry, Disposable temp) {
//    Mono<Integer> mono = chatRepository.count();
//
//  mono.subscribe(res -> {
//      Gauge.builder("chat_metrics", res.longValue(), Long::valueOf).register(registry);
//    });
//
//
//    return Gauge.builder("chat_metrics",
//        temp, Disposable:: )
//        .register(registry);
//  }
}
