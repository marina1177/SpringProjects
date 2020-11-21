package parser.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import parser.repository.StatisticsRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StatisticsTest {

  static final Random RAND = new Random();

  @Autowired
  private StatisticsRepository statRepository;


  @RepeatedTest(100)
  void savestatisticstest() {
    Statistics statObject = getStatObject();
    statRepository.save(statObject);

    List<Statistics> actual = statRepository.findAll();
    assertThat(actual.size()).isOne();
    assertThat(actual.get(0).getOperationTime()).isEqualTo(statObject.getOperationTime());
    assertThat(actual.get(0).getFailLoadMessages()).isEqualTo(statObject.getFailLoadMessages());
    assertThat(actual.get(0).getSuccessLoadMessages())
        .isEqualTo(statObject.getSuccessLoadMessages());
  }

  // https://blog.codeleak.pl/2020/03/spring-boot-tests-with-testcontainers.html

  private Statistics getStatObject() {

    // java.time to Timestamp
    LocalDateTime now = LocalDateTime.now();
    Timestamp timestamp = Timestamp.valueOf(now);

    String topic = "topic.t";
    int successLoadMessages = (RAND.nextInt(100));
    int failLoadMessages = (RAND.nextInt(100));

    Statistics statObject = Statistics.builder()
        .id(0)
        .operationTime(timestamp)
        .topic(topic)
        .successLoadMessages(successLoadMessages)
        .failLoadMessages(failLoadMessages).build();
    return statObject;

  }
}