package ru.yandex.practicum.model.sensor.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.sensor.MotionSensorEvent;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.SensorEventType;
import ru.yandex.practicum.service.MotionService;

@Component
@RequiredArgsConstructor
public class MotionSensorEventHandler implements SensorEventHandler {
    private final MotionService motionService;

    @Override
    public SensorEventType getType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEvent event) {
        MotionSensorEvent motionSensorEvent = (MotionSensorEvent) event;
        motionService.save(motionSensorEvent);
    }
}
