package Map;

import java.util.HashMap;
import java.util.Map;

public class MultySet {

  // неупорядоченная совокупность объектов,
  // в отличие от Set объекты могут повторяться
  // существует потребность определить количество экземпляров объекта

  Map<String, Integer> counts = new HashMap<>();

  void add(String key) {
    Integer count = counts.get(key);
    if (count == null) {
      count = 0;
    }
    counts.put(key, count + 1);
  }

  void add_2(String key) {

    //merge - запись значения в map, но если значение уже есть - вызывается лямбда
    // в качестве аргументов - старое и новое значения (b = 1)
    counts.merge(key, 1,(a,b)->a+b);
  }

}
