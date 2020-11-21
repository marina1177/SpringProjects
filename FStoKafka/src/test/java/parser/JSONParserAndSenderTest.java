package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import parser.domain.JsonParser;
import parser.interfaces.Sender;


class JSONParserAndSenderTest {

  private static JSONParserAndSender parserAndSender;

  static final Random RAND = new Random();
  private RandomStringUtils validGenerator = new RandomStringUtils();

  @Autowired
  private JsonParser parser;

  private static Sender mockSender;

  @BeforeEach
  void setUp() {

    mockSender = mock(Sender.class);
    parserAndSender = new JSONParserAndSender(parser, mockSender);
  }

  @RepeatedTest(100)
  void sentItemsListTest() {

    JSONArray itemsField = new JSONArray();
    Integer numField = (RAND.nextInt(10));

    List<Object> content = createValidJsonRecords(itemsField, numField);
    StatusLoader loadStatus = new StatusLoader();

    loadStatus = parserAndSender.sendItemsList(content);

    assertEquals(numField, loadStatus.getFailLoadMessages());
  }


  List<Object> createValidJsonRecords(JSONArray itemsField, Integer numField) {

    List<Object> expectedContent = new ArrayList<>();

    for (Integer i = 0; i < numField; i++) {
      JSONObject recordContainer = new JSONObject();

      int countRecords = 0;
      while (countRecords++ < 2) {
        String value = validGenerator.random(20);
        String key = validGenerator.random(10);
        recordContainer.put(key, value);
      }
      expectedContent.add(recordContainer.values());
      itemsField.add(recordContainer);
    }
    return expectedContent;
  }
}