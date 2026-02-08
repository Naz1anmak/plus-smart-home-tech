package ru.yandex.practicum.model.hub;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class DeviceAction {
    private String sensorId;
    private ActionType type;
    private Integer value;

    public static List<DeviceActionAvro> toAvroList(List<DeviceAction> list) {
        if (list == null || list.isEmpty()) return Collections.emptyList();
        return list.stream()
                .map(DeviceAction::toAvro)
                .toList();
    }

    private DeviceActionAvro toAvro() {
        return DeviceActionAvro.newBuilder()
                .setSensorId(this.sensorId)
                .setType(ActionTypeAvro.valueOf(this.type.name()))
                .setValue(this.value)
                .build();
    }
}

