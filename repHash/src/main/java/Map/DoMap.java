package Map;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DoMap {

  public void iterateOverMap(Map<String, Integer> map) {
    //получаем набор entry и потом извлекаем и чтото делаем
    //entrySet предпочтительней map.keySet,
    // тк во втором случае код может работать медленней
    // тк при обходе entrySet переход от предыдущего элемента к следующему
    // дешевле чем вызов map.get
    // (в звмсимости от реализации (к/ч дерево(О(log(n),
    // hashMap - вычисление хеш функции, коллизии)
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      String key = entry.getKey();
      Integer value = entry.getValue();
      System.out.println(key + " -> " + value);
    }
  }

  //Collection <V> values() - когда нужны только значения
  public void iterateOverValues(Map<String, Integer> map) {

    for (Integer value : map.values()) {
      System.out.println("Value: " + value);
    }
  }

  // обход в функциональном стиле
  public void iterateOverMap_2(Map<String, Integer> map) {

    // - нельзя досрочно выйти из цикла
    map.forEach((key, value) -> System.out.println(key + " -> " + value));
  }

  // Модификация значений
  public void trimAllValues0(Map<String, String> map) {
    for (String key : map.keySet()){
      String value = map.get(key);
      map.put(key, value.trim());
    }
  }

  public void trimAllValues(Map<String, String> map) {

    for (Map.Entry<String, String> entry : map.entrySet()) {
      entry.setValue(entry.getValue().trim());
    }
  }

  // преимущества интерфейса: replaceAll
  public void trimAllValues_2(Map<String, String> map) {
    map.replaceAll((key, value) -> value.trim());
  }

  // Удаление значений

  public void removeUnwantedValues(Map<String, String> map) {
    for (String key : map.keySet()) {
      String value = map.get(key);
      if (value.equals("foo") || value.equals("bar") || value.equals("baz")) {
        map.remove(key);
      }
    }
  }

  //если вспомнить, что entrySet - обертка над Map,
  // и значит изменения в entrySet = изменения в Map
  public void removeUnwantedValues_2(Map<String, String> map) {

//    while (iterator.hasNext()) {
//      Map.Entry<String, String> entry = iterator.next();
//      if (entry.getValue().equals("foo") ||
//          entry.getValue().equals("bar") ||
//          entry.getValue().equals("baz")) {
//        iterator.remove();
//      }
//    }

    map.entrySet().removeIf(entry ->
        entry.getValue().equals("foo") ||
            entry.getValue().equals("bar") ||
            entry.getValue().equals("baz"));
  }

  // тк в конкретном случае  важны только значения, можно упростить
  // values -  обертка над мап
  public void removeUnwantedValues_3(Map<String, String> map) {
    map.values().removeIf(value ->
        value.equals("foo") ||
            value.equals("bar") ||
            value.equals("baz"));
  }

  private   static final List<String> UNWANTED_VALUES = Arrays.asList("foo", "bar", "baz");

  // + код отдельно - данные отдельно
  public void removeUnwantedValues_4(Map<String, String> map) {
    map.values().removeAll(UNWANTED_VALUES);
  }

}
