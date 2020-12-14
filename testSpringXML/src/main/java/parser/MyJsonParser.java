package parser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


@Slf4j
public class MyJsonParser {

  File jsonFile;

  public MyJsonParser(File jsonFile) {

    this.jsonFile = jsonFile;
  }

  public ArrayList<ArrayList<String>> getItemsList() {
    if (jsonFile != null && jsonFile.exists()) {
      JSONParser jsonParser = new JSONParser();
      try (FileReader reader = new FileReader(jsonFile)) {
        JSONArray a = (JSONArray) jsonParser.parse(reader);
        if (a.isEmpty()) {

          log.info("Empty file");
          return null;
        }
        for (Object o : a) {
          JSONObject root = (JSONObject) o;
          JSONArray items = (JSONArray) root.get("items");
          if (items == null) {
            log.info("the file [{}] does not contain a \"items\" field", jsonFile.getName());
            return null;
          }
          ArrayList<ArrayList<String>> itemsList = new ArrayList<>();
          for (Object el : items) {
            Map<String, Object> field = (Map<String, Object>) el;
            ArrayList<String> tmpList = new ArrayList<>();
            field.forEach((k, v) -> {
              tmpList.add(v.toString());
            });
            itemsList.add(tmpList);
          }
          return itemsList;
        }
      } catch (Exception e) {
        log.info(e.toString());
      }
    }
    return null;
  }

  public void printJsonName() {

    System.out.println(jsonFile.getAbsolutePath());
  }

}
