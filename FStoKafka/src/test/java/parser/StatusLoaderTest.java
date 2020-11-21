package parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StatusLoaderTest {

  static private StatusLoader loadStatus;

  @BeforeEach
  void setUp() {
    loadStatus = new StatusLoader();
  }

  @ParameterizedTest()
  @ValueSource(ints = {1, 3, 5, 15, Integer.MAX_VALUE})
  void addAndGetSuccessMessagesPositiveDeltaTest(int delta) {

    int expectedInt = loadStatus.getSuccessLoadMessages();
    expectedInt += delta;
    loadStatus.addAndGetSuccessMessages(delta);

    assertEquals(expectedInt, loadStatus.getSuccessLoadMessages());
  }

  @ParameterizedTest()
  @ValueSource(ints = {-1, -3, -5, -15, Integer.MIN_VALUE})
  void addAndGetSuccessMessagesNegativeDeltaTest(int delta) {

    int expectedInt = loadStatus.getSuccessLoadMessages();
    loadStatus.addAndGetSuccessMessages(delta);

    assertEquals(expectedInt, loadStatus.getSuccessLoadMessages());
  }

  @ParameterizedTest()
  @ValueSource(ints = {1, 3, 5, 15, Integer.MAX_VALUE})
  void addAndGetFailMessagesPositiveDeltaTest(int delta) {

    int expectedInt = loadStatus.getFailLoadMessages();
    expectedInt += delta;
    loadStatus.addAndGetFailMessages(delta);

    assertEquals(expectedInt, loadStatus.getFailLoadMessages());
  }

  @ParameterizedTest()
  @ValueSource(ints = {-1, -3, -5, -15, Integer.MIN_VALUE})
  void addAndGetFailMessagesNegativeDeltaTest(int delta) {

    int expectedInt = loadStatus.getFailLoadMessages();
    loadStatus.addAndGetFailMessages(delta);

    assertEquals(expectedInt, loadStatus.getFailLoadMessages());
  }

}