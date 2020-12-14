package com.hello;

import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SummaryController {

  private final Counter requestCount;
  private final MyInterface myInterface;

  @GetMapping(value = "/hello")
  public String hello() {
    myInterface.save("hello");
    myInterface.count();

    requestCount.increment();
    requestCount.increment(0.5);

    return "Marina!";
  }
}
