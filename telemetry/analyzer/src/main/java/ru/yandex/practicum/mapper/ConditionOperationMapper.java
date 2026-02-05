package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.model.ConditionOperation;

@Component
public class ConditionOperationMapper {

    public ConditionOperation fromAvro(ConditionOperationAvro avro) {
        return switch (avro) {
            case EQUALS -> ConditionOperation.EQUALS;
            case GREATER_THAN -> ConditionOperation.GREATER_THAN;
            case LOWER_THAN -> ConditionOperation.LOWER_THAN;
        };
    }
}
