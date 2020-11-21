package parser;

import static org.springframework.kafka.test.assertj.KafkaConditions.key;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import parser.interfaces.Sender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.kafka.test.assertj.KafkaConditions.value;

@ExtendWith(SpringExtension.class)
@EmbeddedKafka(
    partitions = 1,
    topics = "${kafka.topic}",
    brokerProperties = "log.dir=$/tmp/kafka.logs/")
@SpringBootTest
class SenderTest {

  @Value("${kafka.topic}")
  private String topic;

  private static String SENDER_TOPIC = "sender";

  @Autowired private EmbeddedKafkaBroker embeddedKafka;

  @Autowired private Sender sender;

  private KafkaMessageListenerContainer<String, String> container;

  private BlockingQueue<ConsumerRecord<String, String>> records;

  @BeforeEach
  public void setUp() throws Exception {

    Map<String, Object> consumerProperties =
        new HashMap<>(KafkaTestUtils.consumerProps("sender", "false", embeddedKafka));

    DefaultKafkaConsumerFactory<String, String> consumerFactory =
        new DefaultKafkaConsumerFactory<String, String>(
            consumerProperties, new StringDeserializer(), new StringDeserializer());

    ContainerProperties containerProperties = new ContainerProperties(SENDER_TOPIC);

    container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

    records = new LinkedBlockingQueue<>();
    container.setupMessageListener((MessageListener<String, String>) records::add);

    container.start();

    ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
  }

  @AfterEach
  void tearDown() {
    container.stop();
  }

  @Test
  public void testSend() throws InterruptedException {

    String greeting = "Hello from test.SenderTest!";
    sender.setTopic(topic);
    sender.send("key", greeting);

    ConsumerRecord<String, String> received = records.poll(100, TimeUnit.MILLISECONDS);

    assertThat(received).has(value(greeting));
    assertThat(received).has(key("key"));
  }
}
