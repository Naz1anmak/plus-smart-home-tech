package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.config.KafkaEventProducer;
import ru.yandex.practicum.config.TopicResolver;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;
import ru.yandex.practicum.model.sensor.SwitchSensorEvent;

@Service
@RequiredArgsConstructor
public class SwitchService {
    private final KafkaEventProducer producer;
    private final TopicResolver topicResolver;

    public void save(SwitchSensorEvent event) {
        SwitchSensorAvro payload = SwitchSensorAvro.newBuilder()
                .setState(event.isState())
                .build();

        SensorEventAvro wrapper = SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();

        String topic = topicResolver.resolve(event.getType());
        String key = event.getHubId();
        producer.send(topic, key, wrapper);
    }
}
