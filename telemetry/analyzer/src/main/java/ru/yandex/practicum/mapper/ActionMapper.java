package ru.yandex.practicum.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.model.Action;

@Component
@RequiredArgsConstructor
public class ActionMapper {
    private final ActionTypeMapper actionTypeMapper;

    public Action fromAvro(DeviceActionAvro avro) {
        Action action = new Action();
        action.setType(actionTypeMapper.fromAvro(avro.getType()));
        action.setValue(action.getValue());
        return action;
    }
}
