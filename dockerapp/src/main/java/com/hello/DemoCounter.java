package com.hello;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoCounter implements Runnable{

  private final Counter beat1, beat2;

  DemoCounter(MeterRegistry meterRegistry) {

    beat1 = Counter
        .builder("demoservice_heart_beat")
        .description("a simple counter")
        .tags("beat", "beat1")
        .register(meterRegistry);
    beat2 = Counter
        .builder("demoservice_heart_beat")
        .description("a simple faster counter")
        .tags("beat", "beat2")
        .register(meterRegistry);
  }

  @Override
  public void run(){
    while (true){
      try{
        Thread.sleep(1000);
        beat1.increment(0.5);
        beat2.increment();

      }catch (InterruptedException e){
        Thread.currentThread().interrupt();
        log.warn("S.o interrapted me and i'll go away :( Bye!");
        return;
      }
    }
  }

}
