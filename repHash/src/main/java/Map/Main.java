package Map;

import java.util.HashMap;
import java.util.Map;

public class Main {

  public static void main(String[] args) {

    Map<String, String> map = new HashMap<>();
    DoMap doMap = new DoMap();

    map.put("1", " Alice ");
    map.put("2", " was   ");
    map.put("3", " beginning ");
    map.put("4", " to get very  ");
    map.put("5", " tired ");

    System.out.println(map.entrySet());
    long m = System.currentTimeMillis();
    System.out.println("_1_");
    doMap.trimAllValues0(map);
    double l1 = System.currentTimeMillis() - m;
    System.out.println("time: "+l1);
    System.out.println("_1_");

    System.out.println(map.entrySet());


    // Обход map

    // модификация map

    // Сценарии для  Map:
      // Multy-map
      // Multy-set

  }
}
