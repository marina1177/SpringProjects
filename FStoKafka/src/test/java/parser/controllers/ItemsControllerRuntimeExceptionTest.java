package parser.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static parser.controllers.ItemsControllerTest.asJsonString;

import io.micrometer.core.instrument.Counter;
import java.nio.file.Path;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import parser.ItemsDirectoryParameters;
import parser.ItemsHandlerParameters;
import parser.JSONParserAndSender;
import parser.LoadFromDirectoryProcess;
import parser.domain.JsonParser;
import parser.interfaces.Sender;
import parser.repository.StatisticsRepository;


@WebMvcTest(controllers = ItemsController.class)
// применяет только конфигурацию, относящуюся к тестам MVC
class ItemsControllerRuntimeExceptionTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private StatisticsRepository statRepository;

  @MockBean
  private Sender sender;

  @MockBean
  private Counter counterSentMsgs;

  @MockBean
  private JsonParser parser;

  private static ItemsDirectoryParameters dp;
  private static ItemsHandlerParameters hp;

  @TempDir
  Path tempDir;

  @BeforeEach
  void setUp() {
    dp = new ItemsDirectoryParameters();
    hp = new ItemsHandlerParameters();
  }

  @TestConfiguration
  public static class TestConfig {

    @Bean
    @Primary
    public LoadFromDirectoryProcess mockDirectoryProcess() throws Exception {
      LoadFromDirectoryProcess mockDirProcess = mock(LoadFromDirectoryProcess.class);
      doThrow(RuntimeException.class).when(mockDirProcess).run();
      return mockDirProcess;
    }

    @Bean
    @Primary
    public JSONParserAndSender mockParserAndSender() throws Exception {
      JSONParserAndSender parserAndSender = mock(JSONParserAndSender.class);
      doThrow(RuntimeException.class).when(parserAndSender)
          .jsonObjectProcess(any(JSONArray.class));
      return parserAndSender;
    }
  }

  @Test
  void postDirectoryRuntimeException() throws Exception {

    dp.setTopic("testTopicName");
    dp.setRootFile(tempDir.toFile());

    mvc.perform(MockMvcRequestBuilders
        .post("/items/dir")
        .content(asJsonString(dp))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  //=============================================================

  @Test
  void postItemsRuntimeException() throws Exception {

    hp.setTopic("testTopicName");
    hp.setItems(new JSONArray());

    mvc.perform(MockMvcRequestBuilders
        .post("/items/handler")
        .content(asJsonString(hp))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}