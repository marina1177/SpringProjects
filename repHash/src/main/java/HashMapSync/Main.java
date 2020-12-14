package HashMapSync;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Main {


  public static void main(String[] args) {

    Map<String, String> currencies = new HashMap<String, String>();
    currencies.put("USA", "USD");
    currencies.put("England", "GBP");
    currencies.put("Canada", "CAD");
    currencies.put("HongKong", "HKD");
    currencies.put("Australia", "AUD");

    currencies = Collections.synchronizedMap(currencies);
    Set<String> keySet = currencies.keySet();
    {
      Iterator<String> itr = keySet.iterator();
      while (itr.hasNext()) {
        System.out.println(itr.next());
      }
    }

  }
}

