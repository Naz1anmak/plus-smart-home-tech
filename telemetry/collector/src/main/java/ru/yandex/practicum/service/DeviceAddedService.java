package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.config.KafkaEventProducer;
import ru.yandex.practicum.config.TopicResolver;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.model.hub.DeviceAddedEvent;

@Service
@RequiredArgsConstructor
public class DeviceAddedService {
    private final KafkaEventProducer producer;
    private final TopicResolver topicResolver;

    public void save(DeviceAddedEvent event) {
        DeviceTypeAvro avroDeviceType = event.getDeviceType().toAvro();

        DeviceAddedEventAvro payload = DeviceAddedEventAvro.newBuilder()
                .setId(event.getId())
                .setType(avroDeviceType)
                .build();

        HubEventAvro wrapper = HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();

        String topic = topicResolver.resolve(event.getType());
        String key = event.getHubId();
        producer.send(topic, wrapper.getTimestamp().toEpochMilli(), key, wrapper);
    }
}
