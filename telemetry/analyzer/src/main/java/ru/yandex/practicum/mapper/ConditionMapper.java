package ru.yandex.practicum.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.TypeNotFoundException;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;
import ru.yandex.practicum.model.Condition;

@Component
@RequiredArgsConstructor
public class ConditionMapper {
    private final ConditionTypeMapper conditionTypeMapper;
    private final ConditionOperationMapper conditionOperationMapper;

    public Condition fromAvro(ScenarioConditionAvro avro) {
        Condition condition = new Condition();
        condition.setType(conditionTypeMapper.fromAvro(avro.getType()));
        condition.setOperation(conditionOperationMapper.fromAvro(avro.getOperation()));
        condition.setValue(mapValue(avro.getValue()));
        return condition;
    }

    private Integer mapValue(Object value) {
        return switch (value) {
            case null -> null;
            case Integer i -> i;
            case Boolean b -> b ? 1 : 0;
            default -> throw new TypeNotFoundException("Тип значения условия не поддерживается: " + value.getClass());
        };
    }
}
