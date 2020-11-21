package parser.controllers;

import io.micrometer.core.instrument.Counter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import parser.StatusLoader;
import parser.entity.Statistics;
import parser.repository.StatisticsRepository;
import parser.LoadFromDirectoryProcess;


@Slf4j
@Controller
@RequiredArgsConstructor
public class Refresh {

  private final StatisticsRepository statRepository;
  private final LoadFromDirectoryProcess directoryProcess;
  private final Counter counterSentMsgs;

  @GetMapping(path = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RestResponse> appRunner() {

    try {
      StatusLoader loadStatus = directoryProcess.run();

      counterSentMsgs.increment(loadStatus.getSuccessLoadMessages());

      RestResponse response = RestResponse.builder()
          .totalSentMsgs(counterSentMsgs.count())
          .successSentMsgs(loadStatus.getSuccessLoadMessages())
          .topicName(directoryProcess.getTopicName()).build();

      // java.time to Timestamp
      LocalDateTime now = LocalDateTime.now();
      Timestamp timestamp = Timestamp.valueOf(now);

      // формирование объекта Statistics  для отправки в бд
      Statistics statObject = Statistics.builder()
          .operationTime(timestamp)
          .topic(directoryProcess.getTopicName())
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