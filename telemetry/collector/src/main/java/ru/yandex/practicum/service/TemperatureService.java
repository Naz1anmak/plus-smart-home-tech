package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.config.KafkaEventProducer;
import ru.yandex.practicum.config.TopicResolver;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;
import ru.yandex.practicum.model.sensor.TemperatureSensorEvent;

@Service
@RequiredArgsConstructor
public class TemperatureService {
    private final KafkaEventProducer producer;
    private final TopicResolver topicResolver;

    public void save(TemperatureSensorEvent event) {
        TemperatureSensorAvro payload = TemperatureSensorAvro.newBuilder()
                .setTemperatureC(event.getTemperatureC())
                .setTemperatureF(event.getTemperatureF())
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
