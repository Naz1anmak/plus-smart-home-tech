package ru.yandex.practicum.handler.sensor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.mapper.grpc.sensor.LightSensorEventMapper;
import ru.yandex.practicum.model.sensor.LightSensorEvent;
import ru.yandex.practicum.service.LightService;

@Component
@RequiredArgsConstructor
public class LightSensorEventHandler implements SensorEventHandler {
    private final LightService lightService;
    private final LightSensorEventMapper mapper;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.LIGHT_SENSOR;
    }

    @Override
    public void handle(SensorEventProto proto) {
        LightSensorEvent lightEvent = mapper.fromProto(proto);
        lightService.save(lightEvent);
    }
}
