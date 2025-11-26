package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.config.KafkaEventProducer;
import ru.yandex.practicum.config.TopicResolver;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.model.hub.ScenarioRemovedEvent;

@Service
@RequiredArgsConstructor
public class ScenarioRemovedService {
    private final KafkaEventProducer producer;
    private final TopicResolver topicResolver;

    public void save(ScenarioRemovedEvent event) {
        ScenarioRemovedEventAvro payload = ScenarioRemovedEventAvro.newBuilder()
                .setName(event.getName())
                .build();

        HubEventAvro wrapper = HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(payload)
                .build();

        String topic = topicResolver.resolve(event.getType());
        String key = event.getHubId();
        producer.send(topic, key, wrapper);
    }
}
