package com.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoggerController {

  @GetMapping("error")
  public String logErrorMessage(@RequestParam("message") String message){
    log.warn("Received Error: {}", message);
    return message;
  }

  @GetMapping("warn")
  public String logWarnMessage(@RequestParam("message") String message){
    log.warn("Received WARN: {}", message);
    return message;
  }

}
