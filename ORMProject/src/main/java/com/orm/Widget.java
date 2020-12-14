package com.orm;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class Widget {
  private final String name;
  private final int id;
}
