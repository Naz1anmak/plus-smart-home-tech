package ru.yandex.practicum.model.sensor.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.SensorEventType;
import ru.yandex.practicum.model.sensor.SwitchSensorEvent;
import ru.yandex.practicum.service.SwitchService;

@Component
@RequiredArgsConstructor
public class SwitchSensorEventHandler implements SensorEventHandler {
    private final SwitchService switchService;

    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEvent event) {
        SwitchSensorEvent switchSensorEvent = (SwitchSensorEvent) event;
        switchService.save(switchSensorEvent);
    }
}
