package ru.yandex.practicum.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventProducer {

    private final KafkaProducer<String, Object> producer;

    public void send(String topic, String key, Object value) {
        ProducerRecord<String, Object> record =
                new ProducerRecord<>(topic, key, value);

        producer.send(record, (md, ex) -> {
            if (ex != null) {
                log.error("Error sending record to topic {}", topic, ex);
                throw new RuntimeException("Kafka send failed", ex);
            }
        });
    }
}
