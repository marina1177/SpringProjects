package parser.domain;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Slf4j
@RequiredArgsConstructor
public class JsonParser {

  private JSONParser jsonParser = new JSONParser();

  private final String fieldName;


  public List<Object> parseProcess(JSONArray multipleJsonObjects, String objectName)
      throws Exception {

    List<Object> itemsRecordsList = new ArrayList<>();
    Integer jsonObjectsCounter = 0;

    for (Object singleJsonObject : multipleJsonObjects) { // parse single json
      JSONArray itemsFieldRecordsArray = (JSONArray) ((JSONObject) singleJsonObject)
          .get(fieldName);
      if (itemsFieldRecordsArray == null) {
        log.warn(
            "jsonObject[{}] in the file {} does not contain a \"{}\" field",
            jsonObjectsCounter,
            objectName,
            fieldName);
        throw new Exception();
      }
      for (Object fieldRecord : itemsFieldRecordsArray) { // save records
        List<Object> singleRecordStore = parseAndSaveFieldRecord(fieldRecord);
        if (singleRecordStore == null) {
          log.warn(
              "jsonObject[{}] in the file {} has the invalid field \"{}\"",
              jsonObjectsCounter,
              objectName,
              fieldName);
          throw new Exception();
        }
        itemsRecordsList.add(singleRecordStore);
      }
      jsonObjectsCounter++;
    }
    return itemsRecordsList;
  }

  public List<Object> getFieldRecordsList(Object obj) throws Exception {

    if (obj.getClass().equals(File.class)) {
      File jsonFile = (File) obj;
      Object multipleObjects = fileValidate(jsonFile);
      if (multipleObjects == null) {
        return new ArrayList<>();
      }
      JSONArray multipleJsonObjects = (JSONArray) multipleObjects;
      return parseProcess(multipleJsonObjects, jsonFile.getName());
    } else if (obj.getClass().equals(JSONArray.class)) {
      JSONArray jsonArray = (JSONArray) obj;
      return parseProcess(jsonArray, "loaded object");
    }
    return new ArrayList<>();
  }

  private List<Object> parseAndSaveFieldRecord(Object fieldRecord) throws Exception {

    List<Object> singleJsonItemsList = new ArrayList<>();

    if (fieldRecord.getClass().equals(JSONObject.class) ||
        fieldRecord.getClass().equals(LinkedHashMap.class)) {

      Map<String, Object> field = (Map<String, Object>) fieldRecord;
      singleJsonItemsList.add(field.values().toString());

    } else {

      log.warn(
          "invalid record format in the \"{}\" field: [{}].\nIt is not JSONObject.class",
          fieldName,
          fieldRecord);
      throw new Exception();
    }
    return singleJsonItemsList;
  }

  private Object fileValidate(File jsonFile) throws Exception {

    if (jsonFile != null && jsonFile.exists()) {
      FileReader reader = new FileReader(jsonFile);
      Object singleObj = jsonParser.parse(reader);
      if (singleObj != null && !singleObj.toString().isEmpty()) {
        return singleObj;
      } else {
        log.info("file [{}] is empty or invalid configuration", jsonFile.getName());
        throw new IOException();
      }
    }
    return null;
  }
}
