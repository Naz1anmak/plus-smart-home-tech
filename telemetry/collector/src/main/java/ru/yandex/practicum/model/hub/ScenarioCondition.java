package ru.yandex.practicum.model.hub;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ScenarioCondition {
    private String sensorId;
    private ScenarioConditionType type;
    private OperationType operation;
    private int value;

    public static List<ScenarioConditionAvro> toAvroList(List<ScenarioCondition> list) {
        if (list == null || list.isEmpty()) return Collections.emptyList();
        return list.stream()
                .map(ScenarioCondition::toAvro)
                .toList();
    }

    private ScenarioConditionAvro toAvro() {
        return ScenarioConditionAvro.newBuilder()
                .setSensorId(this.sensorId)
                .setType(ConditionTypeAvro.valueOf(this.type.name()))
                .setOperation(ConditionOperationAvro.valueOf(this.operation.name()))
                .setValue(this.value)
                .build();
    }
}

enum ScenarioConditionType {
    MOTION,
    LUMINOSITY,
    SWITCH,
    TEMPERATURE,
    CO2LEVEL,
    HUMIDITY
}

enum OperationType {
    EQUALS,
    GREATER_THAN,
    LOWER_THAN
}
