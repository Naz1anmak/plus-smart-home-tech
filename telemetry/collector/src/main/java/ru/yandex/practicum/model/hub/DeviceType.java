package ru.yandex.practicum.model.hub;

import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;

public enum DeviceType {
    MOTION_SENSOR,
    TEMPERATURE_SENSOR,
    LIGHT_SENSOR,
    CLIMATE_SENSOR,
    SWITCH_SENSOR;

    public DeviceTypeAvro toAvro() {
        return DeviceTypeAvro.valueOf(this.name());
    }
}
