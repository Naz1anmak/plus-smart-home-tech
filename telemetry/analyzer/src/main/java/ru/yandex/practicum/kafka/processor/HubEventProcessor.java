package ru.yandex.practicum.kafka.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.config.KafkaConsumerProperties;
import ru.yandex.practicum.kafka.consumer.HubEventConsumerFactory;
import ru.yandex.practicum.kafka.dispatcher.HubEventDispatcher;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubEventProcessor implements Runnable {
    private final HubEventConsumerFactory consumerFactory;
    private final KafkaConsumerProperties consumerProps;
    private final HubEventDispatcher dispatcher;

    @Override
    public void run() {
        KafkaConsumer<String, HubEventAvro> consumer = consumerFactory.create();
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));

        try {
            consumer.subscribe(List.of(consumerProps.getTopics().getHubEvents()));

            while (true) {
                ConsumerRecords<String, HubEventAvro> records = consumer.poll(
                        Duration.ofMillis(consumerProps.getPollTimeoutMs())
                );

                for (ConsumerRecord<String, HubEventAvro> record : records) {
                    log.info("Получено событие от датчика: ключ = {}, значение = {}, раздел = {}, смещение = {}",
                            record.key(), record.value(), record.partition(), record.offset());
                    dispatcher.dispatch(record.value());
                }
                consumer.commitAsync();
            }
        } catch (WakeupException ignored) {
            // shutdown
        } catch (Exception exception) {
            log.error("Ошибка во время обработки событий от датчиков", exception);
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
