package ru.yandex.practicum.handler.sensor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.mapper.grpc.sensor.MotionSensorEventMapper;
import ru.yandex.practicum.model.sensor.MotionSensorEvent;
import ru.yandex.practicum.service.MotionService;

@Component
@RequiredArgsConstructor
public class MotionSensorEventHandler implements SensorEventHandler {
    private final MotionService motionService;
    private final MotionSensorEventMapper mapper;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.MOTION_SENSOR;
    }

    @Override
    public void handle(SensorEventProto proto) {
        MotionSensorEvent motionSensorEvent = mapper.fromProto(proto);
        motionService.save(motionSensorEvent);
    }
}
