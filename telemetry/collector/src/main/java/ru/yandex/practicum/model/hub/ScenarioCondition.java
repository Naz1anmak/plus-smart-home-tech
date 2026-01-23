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
    private Boolean boolValue;
    private Integer intValue;

    public static List<ScenarioConditionAvro> toAvroList(List<ScenarioCondition> list) {
        if (list == null || list.isEmpty()) return Collections.emptyList();
        return list.stream()
                .map(ScenarioCondition::toAvro)
                .toList();
    }

    private ScenarioConditionAvro toAvro() {
        ScenarioConditionAvro.Builder builder = ScenarioConditionAvro.newBuilder()
                .setSensorId(this.sensorId)
                .setType(ConditionTypeAvro.valueOf(this.type.name()))
                .setOperation(ConditionOperationAvro.valueOf(this.operation.name()));

        if (boolValue != null) {
            builder.setValue(boolValue);
        } else if (intValue != null) {
            builder.setValue(intValue);
        } else {
            builder.setValue(null);
        }

        return builder.build();
    }
}
