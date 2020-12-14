package com.mdobro.javakafka.kafkajava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaJavaApplication {

  static Logger kafkaLogger = LoggerFactory.getLogger("com.mdobro.javakafka.kafkajava.kafkaLogger");

  public static void main(String[] args) throws InterruptedException, IllegalArgumentException {
    SpringApplication.run(KafkaJavaApplication.class, args);

    String topic = "test";
    MyProducer producer = new MyProducer(topic);
    /*for (int i = 1; i < 100; i++) {
      producer.send("key_" + i, "Hello from MyProducer");
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }*/
    Runnable task = () -> {
      for (int i = 1; i < 100; i++) {
        producer.send("key_" + i, "Hello from MyProducer");
        try {
          TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      producer.close();
    };
    Thread t = new Thread(task);
    t.start();
    //TimeUnit.MINUTES.sleep(5);

    System.out.println("app topic: "+topic);
    MyConsumer consumer = new MyConsumer(topic);
    consumer.execute(3);

    try {
      Thread.sleep(100000);
    } catch (InterruptedException ie) {

    }
    consumer.close();
  }


}
