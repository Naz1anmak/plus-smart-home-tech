package ru.yandex.practicum.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.config.KafkaConsumerProperties;
import ru.yandex.practicum.kafka.deserializer.SnapshotDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Properties;

@Component
@RequiredArgsConstructor
public class SnapshotConsumerFactory {
    private final KafkaConsumerProperties baseProps;

    public KafkaConsumer<String, SensorsSnapshotAvro> create() {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, baseProps.getBootstrapServers());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, baseProps.isEnableAutoCommit());

        props.put(ConsumerConfig.GROUP_ID_CONFIG, baseProps.getGroups().getSnapshots());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SnapshotDeserializer.class);

        return new KafkaConsumer<>(props);
    }
}
