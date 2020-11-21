package parser.controllers;

import io.micrometer.core.instrument.Counter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import parser.StatusLoader;
import parser.entity.Statistics;
import parser.repository.StatisticsRepository;
import parser.services.JsonLoader;
import parser.JSONParserAndSender;

@Slf4j
@Controller
@RequiredArgsConstructor
public class Loader {

  private final StatisticsRepository statRepository;
  private final JsonLoader loadByURL;
  private final JSONParserAndSender parserAndSender;

  private final Counter counterSentMsgs;

  @GetMapping(path = "/load", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RestResponse> loadMessages() {

    try {
      // загрузить json-массив и обернуть в items
      JSONArray jsonArray = loadByURL.load();
      // распарсить массив и загрузить в кафку
      StatusLoader loadStatus = parserAndSender.jsonObjectProcess(jsonArray);

      counterSentMsgs.increment(loadStatus.getSuccessLoadMessages());

      // собрать и вернуть ответ
      RestResponse response = RestResponse.builder()
          .totalSentMsgs(counterSentMsgs.count())
          .successSentMsgs(loadStatus.getSuccessLoadMessages())
          .topicName(parserAndSender.getTopic()).build();

      // java.time to Timestamp
      LocalDateTime now = LocalDateTime.now();
      Timestamp timestamp = Timestamp.valueOf(now);

      // формирование объекта Statistics  для отправки в бд
      Statistics statObject = Statistics.builder()
          .operationTime(timestamp)
          .topic(response.getTopicName())
          .successLoadMessages(loadStatus.getSuccessLoadMessages())
          .failLoadMessages(loadStatus.getFailLoadMessages()).build();

      // отправка статистики в бд
      statRepository.save(statObject);
      return ResponseEntity.ok(response);

    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
