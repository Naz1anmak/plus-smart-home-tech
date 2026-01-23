package ru.yandex.practicum.model.sensor.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.mapper.grpc.sensor.TemperatureSensorEventMapper;
import ru.yandex.practicum.model.sensor.TemperatureSensorEvent;
import ru.yandex.practicum.service.TemperatureService;

@Component
@RequiredArgsConstructor
public class TemperatureSensorEventHandler implements SensorEventHandler {
    private final TemperatureService temperatureService;
    private final TemperatureSensorEventMapper mapper;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.TEMPERATURE_SENSOR;
    }

    @Override
    public void handle(SensorEventProto proto) {
        TemperatureSensorEvent temperatureSensorEvent = mapper.fromProto(proto);
        temperatureService.save(temperatureSensorEvent);
    }
}
