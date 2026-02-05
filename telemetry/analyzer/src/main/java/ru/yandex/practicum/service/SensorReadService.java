package ru.yandex.practicum.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.exception.ConflictException;
import ru.yandex.practicum.model.Sensor;
import ru.yandex.practicum.repository.SensorRepository;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorReadService {
    private final SensorRepository sensorRepository;

    @Transactional(readOnly = true)
    public void throwIfExists(Collection<String> ids, String hubId) {
        if (sensorRepository.existsByIdInAndHubId(ids, hubId)) {
            log.error("Один из датчиков с id={} уже существует в хабе id={}", ids, hubId);
            throw new ConflictException("Один из датчиков с id=" + ids + " уже существует в хабе id=" + hubId);
        }
    }

    @Transactional(readOnly = true)
    public Sensor findByIdAndHubId(String id, String hubId) {
        return sensorRepository.findByIdAndHubId(id, hubId).orElseThrow(() -> {
            log.error("Датчик с id={} не найден в хабе id={}", id, hubId);
            return new EntityNotFoundException("Датчик с id=" + id + " не найден в хабе id=" + hubId);
        });
    }
}
