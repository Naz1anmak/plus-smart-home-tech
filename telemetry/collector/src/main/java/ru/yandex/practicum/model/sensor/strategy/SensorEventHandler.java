package ru.yandex.practicum.model.sensor.strategy;

import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.SensorEventType;

public interface SensorEventHandler {

    SensorEventType getType();

    void handle(SensorEvent event);
}
