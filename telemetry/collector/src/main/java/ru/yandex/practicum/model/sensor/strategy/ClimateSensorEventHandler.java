package ru.yandex.practicum.model.sensor.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.mapper.grpc.sensor.ClimateSensorEventMapper;
import ru.yandex.practicum.model.sensor.ClimateSensorEvent;
import ru.yandex.practicum.service.ClimateService;

@Component
@RequiredArgsConstructor
public class ClimateSensorEventHandler implements SensorEventHandler {
    private final ClimateService climateService;
    private final ClimateSensorEventMapper mapper;

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.CLIMATE_SENSOR;
    }

    @Override
    public void handle(SensorEventProto proto) {
        ClimateSensorEvent climateSensorEvent = mapper.fromProto(proto);
        climateService.save(climateSensorEvent);
    }
}
