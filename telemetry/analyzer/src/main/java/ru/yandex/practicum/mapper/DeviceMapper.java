package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.Sensor;

@Component
public class DeviceMapper {

    public Sensor fromAvro(String hubId, String sensorId) {
        Sensor sensor = new Sensor();
        sensor.setId(sensorId);
        sensor.setHubId(hubId);
        return sensor;
    }
}
