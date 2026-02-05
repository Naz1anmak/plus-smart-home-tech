package ru.yandex.practicum.kafka.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.config.KafkaConsumerProperties;
import ru.yandex.practicum.kafka.consumer.SnapshotConsumerFactory;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.service.ScenarioExecutionService;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SnapshotProcessor implements Runnable {
    private final SnapshotConsumerFactory consumerFactory;
    private final KafkaConsumerProperties consumerProps;
    private final ScenarioExecutionService scenarioExecutionService;

    @Override
    public void run() {
        KafkaConsumer<String, SensorsSnapshotAvro> consumer = consumerFactory.create();
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));

        try {
            consumer.subscribe(List.of(consumerProps.getTopics().getSnapshots()));

            while (true) {
                ConsumerRecords<String, SensorsSnapshotAvro> records = consumer.poll(
                        Duration.ofMillis(consumerProps.getPollTimeoutMs())
                );

                for (ConsumerRecord<String, SensorsSnapshotAvro> record : records) {
                    log.info("Получен снепшот состояния датчиков: ключ = {}, значение = {}, раздел = {}, смещение = {}",
                            record.key(), record.value(), record.partition(), record.offset());
                    scenarioExecutionService.process(record.value());
                }
                consumer.commitAsync();
            }
        } catch (WakeupException exception) {
            log.info("Получен сигнал завершения работы, останавливаем процессор снепшотов");
        } catch (Exception exception) {
            log.error("Ошибка во время обработки снепшотов", exception);
        } finally {
            try {
                consumer.commitSync();
            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();
            }
        }
    }
}
