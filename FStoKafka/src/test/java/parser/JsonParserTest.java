package parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.text.RandomStringGenerator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import parser.domain.JsonParser;


class JsonParserTest {

  static final Random RAND = new Random();
  private RandomStringGenerator validGenerator =
      new RandomStringGenerator.Builder().withinRange('a', 'z').build();
  private RandomStringGenerator invalidGenerator =
      new RandomStringGenerator.Builder().withinRange('!', 'z').build();

  private JsonParser parser = new JsonParser("items");
  private JSONObject resultObject = new JSONObject();
  private JSONArray itemsField = new JSONArray();
  private JSONArray resultJsonArray = new JSONArray();
  private static Integer expectedNumField = (RAND.nextInt(10));

  @TempDir static Path tempDir;

  private static Path filePath;

  @BeforeAll
  static void setUp() {
    filePath = tempDir.resolve("test.json");
    if (expectedNumField % 2 != 0) {
      expectedNumField++;
    }
  }

  List<Object> createValidJsonRecords(JSONArray itemsField, Integer numField) {

    List<Object> expectedContent = new ArrayList<>();

    for (Integer i = 0; i < numField; i++) {
      JSONObject recordContainer = new JSONObject();

      int countRecords = 0;
      while (countRecords++ < 2) {
        String value = validGenerator.generate(20);
        String key = validGenerator.generate(10);
        recordContainer.put(key, value);
      }
      expectedContent.add(recordContainer.values());
      itemsField.add(recordContainer);
    }
    return expectedContent;
  }

  void createInvalidJsonRecords(JSONArray itemsField, Integer numField) {

    List<Object> expectedContent = new ArrayList<>();

    for (Integer i = 0; i < numField; i++) {
      String value = invalidGenerator.generate(20);
      itemsField.add(value);
    }
  }

  void writeToFile(File testFile, JSONArray jsonArray) throws IOException {
    FileWriter file = new FileWriter(testFile);
    for (Object jsonObject : jsonArray) file.write(jsonArray.toJSONString());
    file.flush();
  }

  @Test
  void SeveralWrongRecordsTest() throws Exception {

    //  когда несколько записей в файле неправильные
    List<Object> expectedContent = createValidJsonRecords(itemsField, expectedNumField / 2);
    createInvalidJsonRecords(itemsField, expectedNumField / 2);

    resultObject.put("items", itemsField);
    resultJsonArray.add(resultObject);
    writeToFile(filePath.toFile(), resultJsonArray);

    List<Object> actualItemsList = parser.getFieldRecordsList(filePath.toFile());
    int emptyRecords = 0;
    int fullRecords = 0;
    for (int indx = 0; indx < actualItemsList.size(); indx++) {
      Object record = actualItemsList.get(indx);
      if (record.getClass().equals(ArrayList.class)) {
        if (((List<Object>) record).isEmpty()) {
          emptyRecords++;
        } else {
          fullRecords++;
        }
      }
    }
    assertEquals(expectedContent.size(), fullRecords);
    assertEquals(expectedNumField / 2, emptyRecords);
  }

  @Test
  void InvalidJsonRecordsTest()  {

    try {
    //  когда весь файл неправильный
    createInvalidJsonRecords(itemsField, expectedNumField);

    resultObject.put("items", itemsField);
    resultJsonArray.add(resultObject);
    writeToFile(filePath.toFile(), resultJsonArray);


      List<Object> actualItemsList = parser.getFieldRecordsList(filePath.toFile());

      for (Object record : actualItemsList) {
        assertTrue(((List<Object>) record).isEmpty());
      }
    }catch (Exception e){
      assertFalse(true);
    }
  }


  @Test
  void ValidJsonRecordsTest() throws Exception {

    //  когда все записи в файле правильные
    List<Object> expectedContent = createValidJsonRecords(itemsField, expectedNumField);

    resultObject.put("items", itemsField);
    resultJsonArray.add(resultObject);
    writeToFile(filePath.toFile(), resultJsonArray);

    List<Object> actualitemsList = parser.getFieldRecordsList(filePath.toFile());

    for (int indx = 0; indx < actualitemsList.size(); indx++) {
      if (actualitemsList.get(indx).getClass().equals(ArrayList.class)) {
        ArrayList<Object> record = (ArrayList<Object>) actualitemsList.get(indx);
        String expectedString = expectedContent.get(indx).toString();
        String actualString = record.get(0).toString();
        assertTrue(actualString.equals(expectedString));
      } else {
        assertFalse(true);
      }
    }
  }
}

// сгенерировать 3 случая массива записей для поля items:
  // 2 функции - для валидных и невалидных записей +функция для формирования и записи в файл
  // 3 теста
//  когда все записи в файле правильные
//  когда несколько записей в файле неправильные
//  когда весь файл неправильный
