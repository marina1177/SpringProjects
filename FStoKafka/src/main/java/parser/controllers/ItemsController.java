package parser.controllers;

import io.micrometer.core.instrument.Counter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import parser.ItemsDirectoryParameters;
import parser.ItemsHandlerParameters;
import parser.JSONParserAndSender;
import parser.LoadFromDirectoryProcess;
import parser.StatusLoader;
import parser.entity.Statistics;
import parser.repository.StatisticsRepository;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemsController {

  private final StatisticsRepository statRepository;
  private final JSONParserAndSender parserAndSender;
  private final LoadFromDirectoryProcess directoryProcess;
  private final Counter counterSentMsgs;

  @Value("${spring.fieldName}")
  private String fieldName;

  @PostMapping(value = "/dir")
  public ResponseEntity<StatusLoader> postDirectory(
      @Valid @RequestBody ItemsDirectoryParameters directoryParameters) {

    if (directoryParameters == null
        || directoryParameters.getRootFile() == null
        || !directoryParameters.getRootFile().exists()) {
      return ResponseEntity.badRequest().build();
    }

    // настроить топик
    parserAndSender.setTopic(directoryParameters.getTopic());

    //  настроить директорию
    directoryProcess.setRootFile(directoryParameters.getRootFile());

    try {
      // запустить процесс поиска джсонов в директории и отправки в кафку items
      StatusLoader response = directoryProcess.run();

      // увеличить счетчик всех сообщений,
      // которые были отправлены с запуска приложения
      counterSentMsgs.increment(response.getSuccessLoadMessages());

      // формирование объекта Statistics  для отправки в бд
      // java.time to Timestamp
      LocalDateTime now = LocalDateTime.now();
      Timestamp timestamp = Timestamp.valueOf(now);
      Statistics statObject = Statistics.builder()
          .operationTime(timestamp)
          .topic(directoryParameters.getTopic())
          .successLoadMessages(response.getSuccessLoadMessages())
          .failLoadMessages(response.getFailLoadMessages()).build();

      // отправка статистики в бд
      statRepository.save(statObject);
      log.info("directoryParameters saved successfully:\n{}", directoryParameters);

      // вернуть ответ
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(value = "/handler")
  public ResponseEntity<StatusLoader> postItems(
      @Valid @NotEmpty @RequestBody ItemsHandlerParameters handlerParameters) {

    if (handlerParameters == null) {
      return ResponseEntity.badRequest().build();
    }
    // настроить топик
    parserAndSender.setTopic(handlerParameters.getTopic());

    JSONObject newJsonObject = new JSONObject();
    newJsonObject.put(fieldName, handlerParameters.getItems());

    JSONArray resultArray = new JSONArray();
    resultArray.add(newJsonObject);

    try {
      // распарсить массив и загрузить в кафку
      StatusLoader response = parserAndSender.jsonObjectProcess(resultArray);

      // увеличить счетчик всех сообщений,
      // которые были отправлены с запуска приложения
      counterSentMsgs.increment(response.getSuccessLoadMessages());

      // joda.time to Timestamp
      LocalDateTime now = LocalDateTime.now();
      Timestamp timestamp = Timestamp.valueOf(now);

      // формирование объекта Statistics  для отправки в бд
      Statistics statObject = Statistics.builder()
          .operationTime(timestamp)
          .topic(handlerParameters.getTopic())
          .successLoadMessages(response.getSuccessLoadMessages())
          .failLoadMessages(response.getFailLoadMessages()).build();

      // отправка статистики в бд
      statRepository.save(statObject);
      log.info("handlerParameters saved successfully:\n{}", handlerParameters);

      // вернуть ответ
      return ResponseEntity.ok(response);

    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
