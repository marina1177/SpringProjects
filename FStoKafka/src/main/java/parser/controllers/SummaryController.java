package parser.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SummaryController {

//  private Counter requestCount;

//  public void CounterController(CollectorRegistry collectorRegistry) {
//    requestCount = Counter.build()
//        .name("request_count")
//        .help("Number of hello requests.")
//        .register(collectorRegistry);
//  }

  @GetMapping(value = "/hello")
  public String hello() {
//    requestCount.inc();
//    requestCount.inc(0.5);

    return "Hi!";
  }
}
