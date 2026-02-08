package ru.yandex.practicum.mapper.grpc.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.TypeNotFoundException;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.model.hub.ActionType;
import ru.yandex.practicum.model.hub.DeviceAction;

@Component
public class DeviceActionMapper {

    public DeviceAction fromProto(DeviceActionProto proto) {
        DeviceAction action = new DeviceAction();
        action.setSensorId(proto.getSensorId());
        action.setType(mapType(proto.getType()));
        if (proto.hasValue()) {
            action.setValue(proto.getValue());
        }
        return action;
    }

    private ActionType mapType(ActionTypeProto typeProto) {
        return switch (typeProto) {
            case ACTIVATE -> ActionType.ACTIVATE;
            case DEACTIVATE -> ActionType.DEACTIVATE;
            case INVERSE -> ActionType.INVERSE;
            case SET_VALUE -> ActionType.SET_VALUE;
            case UNRECOGNIZED -> throw new TypeNotFoundException("Неизвестный тип действия: " + typeProto);
        };
    }
}
