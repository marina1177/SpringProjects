package com.mdobro.javakafka.kafkajava;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;


public class MyConsumer {

  private String topic;
  private KafkaConsumer<String, String> consumer;
  private ExecutorService executor;

  public MyConsumer(String topic) {
    this.topic = topic;
    this.consumer = getConsumer();
    this.consumer.subscribe(Collections.singletonList(topic));
  }



 // private KafkaConsumer<String, String> consumer = getConsumer();

  private KafkaConsumer<String, String> getConsumer() {
    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.53.67.35:9092");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "groupId");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

    KafkaConsumer<String, String> tmpConsumer = new KafkaConsumer<>(props);

    //нужно подписаться на топик/и
    System.out.println("consume topic: "+topic);
    //tmpConsumer.subscribe(Collections.singletonList(topic));

    return tmpConsumer;
  }

  public void execute(int numberOfThreads) {

    // Initialize a ThreadPool with size = 5 and use the BlockingQueue with size =1000 to
    // hold submitted tasks.
    executor = new ThreadPoolExecutor(numberOfThreads, numberOfThreads, 0L, TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(100);
      for (final ConsumerRecord record : records) {
        executor.submit(new ConsumerThreadHandler(record));
      }
    }
  }


  public void run() {

    Executors.newSingleThreadExecutor().execute(() -> {
      while (true) {
        try {
          ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
          for (ConsumerRecord<String, String> record : records) {
            System.out.println(
                "Received message: (" + record.key() + ", " + record.value() + ") at offset "
                    + record.offset());
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });

  }


  public void consume(Consumer<ConsumerRecord<String, String>> recordConsume) {

    Runnable task = () -> {
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
        records.forEach(record -> {
          recordConsume.accept(record);
        });
      }
    };

    new Thread(task).start();
  }

  public void close() {
    consumer.close();
  }

}
