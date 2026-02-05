package ru.yandex.practicum.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.model.Action;
import ru.yandex.practicum.model.Condition;
import ru.yandex.practicum.model.Scenario;

@Component
@RequiredArgsConstructor
public class ScenarioMapper {
    private final ConditionMapper conditionMapper;
    private final ActionMapper actionMapper;

    public Scenario fromAvro(String hubId, ScenarioAddedEventAvro avro) {
        Scenario scenario = new Scenario();
        scenario.setHubId(hubId);
        scenario.setName(avro.getName());

        for (ScenarioConditionAvro avroCondition : avro.getConditions()) {
            Condition condition = conditionMapper.fromAvro(avroCondition);
            scenario.addCondition(avroCondition.getSensorId(), condition);
        }

        for (DeviceActionAvro avroAction : avro.getActions()) {
            Action action = actionMapper.fromAvro(avroAction);
            scenario.addAction(avroAction.getSensorId(), action);
        }

        return scenario;
    }
}


