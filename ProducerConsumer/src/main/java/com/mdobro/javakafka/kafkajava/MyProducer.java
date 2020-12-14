package com.mdobro.javakafka.kafkajava;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

@Slf4j
public class MyProducer {

	String topic;

	public MyProducer(String topic) {

		this.topic = topic;
	}


	private KafkaProducer producer = getProducer();

	private KafkaProducer<String, String> getProducer() {

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.53.67.35:9092");
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "clientId");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		return new KafkaProducer<>(props);
	}

	void send(String key, String value) {

		System.out.println("produce topic: "+topic);
		ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);
		producer.send(producerRecord);
	}

	public void close() {
		log.info("Flushing and closing producer");
		producer.flush();
		producer.close(10_000, TimeUnit.MILLISECONDS);

		//producer.close();
	}

}


