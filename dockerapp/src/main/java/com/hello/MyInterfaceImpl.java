package com.hello;

import java.util.ArrayList;
import java.util.List;

public class MyInterfaceImpl implements MyInterface {

  private List list = new ArrayList();

  @Override
  public void save(String string) {
    list.add(string);
  }

  @Override
  public Integer count() {
    return list.size();
  }
}
