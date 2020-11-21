package com.hello;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.metrics.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SummaryController {

 private final Counter requestCount;

  @GetMapping(value = "/hello")
  public String hello() {
    requestCount.increment();
    requestCount.increment(0.5);

    return "Hi!";
  }
}
