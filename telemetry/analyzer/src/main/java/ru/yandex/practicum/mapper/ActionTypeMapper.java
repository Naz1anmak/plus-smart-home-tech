package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.TypeNotFoundException;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;
import ru.yandex.practicum.model.ActionType;

@Component
public class ActionTypeMapper {

    public ActionType fromAvro(ActionTypeAvro avro) {
        return switch (avro.toString()) {
            case "ACTIVATE" -> ActionType.ACTIVATE;
            case "DEACTIVATE" -> ActionType.DEACTIVATE;
            case "INVERSE" -> ActionType.INVERSE;
            case "SET_VALUE" -> ActionType.SET_VALUE;
            default -> throw new TypeNotFoundException("Неизвестный тип действия: " + avro);
        };
    }
}
