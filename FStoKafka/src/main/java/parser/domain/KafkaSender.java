package parser.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import parser.interfaces.Sender;

@Slf4j
@Data
public class KafkaSender implements Sender, AutoCloseable {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Value("${kafka.topic:sender}")
  private String topic;

  public int send(String key, String value) {

    try {
      ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);

      // синхронная отправка
      // .get() - генерирует исключение в случае неудачи отправки записи в Kafka
      kafkaTemplate.send(producerRecord).get();
      return 1;
    } catch (Exception e) {
      log.warn("Send Fail: {}", e.toString());
    }
    return 0;
  }

  @Override
  public void close() {

    log.info("Flushing producer");
    kafkaTemplate.flush();
  }
}
