package ru.yandex.practicum.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.KafkaConsumerProperties;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.service.SensorsSnapshotService;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {
    private final KafkaProducer<String, SpecificRecordBase> producer;
    private final KafkaConsumer<String, SensorEventAvro> consumer;
    private final SensorsSnapshotService sensorsSnapshotService;
    private final KafkaConsumerProperties properties;

    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));

        try {
            consumer.subscribe(List.of(properties.getTopics().getSensors()));

            while (true) {
                ConsumerRecords<String, SensorEventAvro> records = consumer.poll(
                        Duration.ofMillis(properties.getPollTimeoutMs())
                );

                for (ConsumerRecord<String, SensorEventAvro> record : records) {
                    log.info("Получено событие от датчика: ключ = {}, значение = {}, раздел = {}, смещение = {}",
                            record.key(), record.value(), record.partition(), record.offset());

                    Optional<SensorsSnapshotAvro> snapshot = sensorsSnapshotService.updateState(record.value());

                    snapshot.ifPresent(s -> producer.send(
                            new ProducerRecord<>(
                                    "telemetry.snapshots.v1",
                                    s.getHubId(),
                                    s
                            )
                    ));
                }
                consumer.commitAsync();
            }
        } catch (WakeupException ignored) {
            // shutdown
        } catch (Exception exception) {
            log.error("Ошибка во время обработки событий от датчиков", exception);
        } finally {
            try {
                producer.flush();
                consumer.commitSync();
            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();
                log.info("Закрываем продюсер");
                producer.close();
            }
        }
    }
}
