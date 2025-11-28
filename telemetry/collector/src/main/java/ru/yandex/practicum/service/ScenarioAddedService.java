package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.config.KafkaEventProducer;
import ru.yandex.practicum.config.TopicResolver;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.model.hub.DeviceAction;
import ru.yandex.practicum.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.model.hub.ScenarioCondition;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioAddedService {
    private final KafkaEventProducer producer;
    private final TopicResolver topicResolver;

    public void save(ScenarioAddedEvent event) {
        List<ScenarioConditionAvro> conditionAvro = ScenarioCondition.toAvroList(event.getConditions());
        List<DeviceActionAvro> actionAvro = DeviceAction.toAvroList(event.getActions());

        ScenarioAddedEventAvro payload = ScenarioAddedEventAvro.newBuilder()
                .setName(event.getName())
                .setConditions(conditionAvro)
                .setActions(actionAvro)
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
