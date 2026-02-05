package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mapper.DeviceMapper;
import ru.yandex.practicum.model.Sensor;
import ru.yandex.practicum.repository.SensorRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;
    private final SensorReadService sensorReadService;
    private final DeviceMapper mapper;

    @Transactional
    public void addSensor(String hubId, String sensorId) {
        sensorReadService.existsByIdInAndHubId(List.of(sensorId), hubId);
        Sensor sensor = mapper.fromAvro(hubId, sensorId);
        sensorRepository.save(sensor);
        log.info("Датчик с id={} добавлен", sensor.getId());
    }

    @Transactional
    public void removeSensor(String hubId, String sensorId) {
        Sensor sensor = sensorReadService.findByIdAndHubId(sensorId, hubId);
        sensorRepository.delete(sensor);
        log.info("Датчик с id={} удален из хаба id={}", sensorId, hubId);
    }
}
