package com.hello.catalog;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CatalogClient {

  private final DemoCounters demoCounters;

  public static CatalogClient from(DemoCounters demoCounters) {
    log.info("Configuring http client");

    return new CatalogClient(demoCounters);
  }

  public void getCardsFromCatalog() {

    log.info("Got answer from catalog");

    demoCounters.getGauge().set(12);
    demoCounters.getCounterSuccessRate().increment();
    demoCounters.getCounterFailRate().increment(0.5);
  }

}
