package com.hello;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Scheduler {

  private final AtomicInteger testGauge;
  private final Counter testCounter;

  @Autowired
  public Scheduler(MeterRegistry meterRegistry) {
    // Counter vs. gauge, summary vs. histogram
    // https://prometheus.io/docs/practices/instrumentation/#counter-vs-gauge-summary-vs-histogram
    testGauge = meterRegistry.gauge("custom_gauge", new AtomicInteger(0));
    testCounter = meterRegistry.counter("custom_counter");
  }

  @Scheduled(fixedRateString = "1000", initialDelayString = "0")
  public String schedulingTask() {
    testGauge.set(Scheduler.getRandomNumberInRange(0, 100));

    testCounter.increment();

    return "Sheduler";
  }

  private static int getRandomNumberInRange(int min, int max) {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }

    Random r = new Random();
    return r.nextInt((max - min) + 1) + min;
  }
}
