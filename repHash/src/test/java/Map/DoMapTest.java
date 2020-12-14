package Map;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DoMapTest {


  @Test
  void trimTest(){

    Map<String, String> map = new HashMap<>();
    DoMap doMap = new DoMap();

    map.put("1", " Alice ");
    map.put("2", " was   ");
    map.put("3", " beginning ");
    map.put("4", " to get very  ");
    map.put("5", " tired ");

    long m = System.currentTimeMillis();
    doMap.trimAllValues0(map);
    System.out.println((double) (System.currentTimeMillis() - m));
    System.out.println(map.entrySet());
    assertTrue(true);

  }

}