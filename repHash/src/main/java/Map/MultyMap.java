package Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultyMap {

  // существую библиотеки с отдельной реализацией и апи для мульти-мап
  Map<String, List<String>> multyMap = new HashMap<>();

  void add(String key, String value) {
    List<String> list = multyMap.get(key);
    if (list == null) {
      list = new ArrayList<>();
      multyMap.put(key, list);
    }
    list.add(value);
  }

  // СomputeIfAbsent!
  void add_2(String key, String value) {
    multyMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
  }


}
