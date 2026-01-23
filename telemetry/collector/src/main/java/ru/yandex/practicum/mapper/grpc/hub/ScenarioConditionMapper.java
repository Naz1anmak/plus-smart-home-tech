package ru.yandex.practicum.mapper.grpc.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.TypeNotFoundException;
import ru.yandex.practicum.grpc.telemetry.event.ConditionOperationProto;
import ru.yandex.practicum.grpc.telemetry.event.ConditionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;
import ru.yandex.practicum.model.hub.OperationType;
import ru.yandex.practicum.model.hub.ScenarioCondition;
import ru.yandex.practicum.model.hub.ScenarioConditionType;

@Component
public class ScenarioConditionMapper {

    public ScenarioCondition fromProto(ScenarioConditionProto proto) {
        ScenarioCondition condition = new ScenarioCondition();
        condition.setSensorId(proto.getSensorId());
        condition.setType(mapType(proto.getType()));
        condition.setOperation(mapOperation(proto.getOperation()));
        mapValue(proto, condition);
        return condition;
    }

    private ScenarioConditionType mapType(ConditionTypeProto protoType) {
        return switch (protoType) {
            case MOTION -> ScenarioConditionType.MOTION;
            case LUMINOSITY -> ScenarioConditionType.LUMINOSITY;
            case SWITCH -> ScenarioConditionType.SWITCH;
            case TEMPERATURE -> ScenarioConditionType.TEMPERATURE;
            case CO2LEVEL -> ScenarioConditionType.CO2LEVEL;
            case HUMIDITY -> ScenarioConditionType.HUMIDITY;
            case UNRECOGNIZED -> throw new TypeNotFoundException("Неизвестный тип условия сценария: " + protoType);
        };
    }

    private OperationType mapOperation(ConditionOperationProto protoType) {
        return switch (protoType) {
            case EQUALS -> OperationType.EQUALS;
            case GREATER_THAN -> OperationType.GREATER_THAN;
            case LOWER_THAN -> OperationType.LOWER_THAN;
            case UNRECOGNIZED ->
                    throw new TypeNotFoundException("Неизвестный тип операции условия сценария: " + protoType);
        };
    }

    private void mapValue(ScenarioConditionProto proto, ScenarioCondition condition) {
        switch (proto.getValueCase()) {
            case BOOL_VALUE -> condition.setBoolValue(proto.getBoolValue());
            case INT_VALUE -> condition.setIntValue(proto.getIntValue());
            case VALUE_NOT_SET -> {
            }
        }
    }
}
