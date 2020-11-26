package com.hello.catalog;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ServiceController {

  private final CatalogClient catalogClient;

  @GetMapping(value = "/service")
  public String load(){
    catalogClient.getCardsFromCatalog();
    return "getCatalog";
  }
}
