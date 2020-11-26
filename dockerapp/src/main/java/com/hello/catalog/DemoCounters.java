package com.hello.catalog;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@RequiredArgsConstructor
public class DemoCounters {

  private final Counter counterSuccessRate, counterFailRate;
  private final AtomicInteger gauge;

  public static DemoCounters init(final MeterRegistry meterRegistry) {

    return new DemoCounters(
        Counter.builder("catalog_counter").tags("counter", "SuccessRate").register(meterRegistry),
        Counter.builder("catalog_counter").tags("counter", "FailRate").register(meterRegistry),
        meterRegistry.gauge("catalogGauge", new AtomicInteger(0)));
  }
}

