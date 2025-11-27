package ru.yandex.practicum.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventProducer {
    private final KafkaProducer<String, SpecificRecordBase> producer;

    public CompletableFuture<RecordMetadata> send(String topic, long timestamp, String key, SpecificRecordBase value) {
        ProducerRecord<String, SpecificRecordBase> record =
                new ProducerRecord<>(topic, null, timestamp, key, value);

        CompletableFuture<RecordMetadata> future = new CompletableFuture<>();
        producer.send(record, (md, ex) -> {
            if (ex != null) {
                log.error("Error sending record to topic {}", topic, ex);
                future.completeExceptionally(ex);
            } else {
                future.complete(md);
            }
        });
        return future;
    }
}
