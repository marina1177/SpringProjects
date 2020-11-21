package parser.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Counter;
import java.io.File;
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
import parser.StatusLoader;
import parser.domain.JsonParser;
import parser.interfaces.Sender;
import parser.repository.StatisticsRepository;


@WebMvcTest(controllers = ItemsController.class)
// применяет только конфигурацию, относящуюся к тестам MVC
class ItemsControllerTest {

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
      LoadFromDirectoryProcess ldp = mock(LoadFromDirectoryProcess.class);
      when(ldp.run()).thenReturn(new StatusLoader());
      return ldp;
    }

    @Bean
    @Primary
    public JSONParserAndSender mockParserAndSender() throws Exception {
      JSONParserAndSender parserAndSender = mock(JSONParserAndSender.class);

      when(parserAndSender.jsonObjectProcess(any(JSONArray.class))).thenReturn(new StatusLoader());

      return parserAndSender;
    }
  }

  @Test
  void postDirectory() throws Exception {

    dp.setTopic("testTopicName");
    dp.setRootFile(tempDir.toFile());

    mvc.perform(MockMvcRequestBuilders
        .post("/items/dir")
        .content(asJsonString(dp))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(result -> {
          assertEquals("application/json", result.getResponse().getContentType());
        });
  }

  @Test
  void postDirectoryNullParameters() throws Exception {

    mvc.perform(MockMvcRequestBuilders
        .post("/items/dir")
        .content(asJsonString(dp))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void postDirectoryIncorrectDirectoryParameters() throws Exception {
    dp.setTopic("testTopicName");
    dp.setRootFile(new File("testDirName"));

    mvc.perform(MockMvcRequestBuilders
        .post("/items/dir")
        .content(asJsonString(dp))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void postDirectoryEmptyTopicInDirectoryParameters() throws Exception {

    dp.setRootFile(
        tempDir.toFile());

    mvc.perform(MockMvcRequestBuilders
        .post("/items/dir")
        .content(asJsonString(dp))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  //=============================================================

  @Test
  void postItems() throws Exception {

    hp.setTopic("testTopicName");
    hp.setItems(new JSONArray());

    mvc.perform(MockMvcRequestBuilders
        .post("/items/handler")
        .content(asJsonString(hp))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(result -> {
          assertEquals("application/json", result.getResponse().getContentType());
        });
  }

  @Test
  void postItemsNullParameters() throws Exception {

    mvc.perform(MockMvcRequestBuilders
        .post("/items/handler")
        .content(asJsonString(hp))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  public static String asJsonString(final Object obj) {
    if (obj == null) {
      return new String();
    }
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}