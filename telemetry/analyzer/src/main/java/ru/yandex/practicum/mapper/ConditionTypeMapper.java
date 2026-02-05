package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.TypeNotFoundException;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.model.ConditionType;

@Component
public class ConditionTypeMapper {

    public ConditionType fromAvro(ConditionTypeAvro avro) {
        return switch (avro.toString()) {
            case "MOTION" -> ConditionType.MOTION;
            case "LUMINOSITY" -> ConditionType.LUMINOSITY;
            case "SWITCH" -> ConditionType.SWITCH;
            case "TEMPERATURE" -> ConditionType.TEMPERATURE;
            case "CO2LEVEL" -> ConditionType.CO2LEVEL;
            case "HUMIDITY" -> ConditionType.HUMIDITY;
            default -> throw new TypeNotFoundException("Неизвестный тип условия: " + avro);
        };
    }
}
