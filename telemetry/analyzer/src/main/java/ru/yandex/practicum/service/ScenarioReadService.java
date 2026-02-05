package ru.yandex.practicum.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.model.Scenario;
import ru.yandex.practicum.repository.ScenarioRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScenarioReadService {
    private final ScenarioRepository scenarioRepository;

    @Transactional(readOnly = true)
    public Scenario findByHubIdAndName(String hubId, String name) {
        return scenarioRepository.findByHubIdAndName(hubId, name).orElseThrow(() -> {
            log.error("Сценарий с именем '{}' для хаба с id='{}' не найден", name, hubId);
            return new EntityNotFoundException("Сценарий с именем '" + name + "' для хаба с id='" + hubId + "' не найден");
        });
    }

    @Transactional(readOnly = true)
    public void existsByHubIdAndName(String hubId, String name) {
        scenarioRepository.findByHubId(hubId).stream()
                .filter(scenario -> scenario.getName().equals(name))
                .findAny()
                .ifPresent(scenario -> {
                    log.error("Сценарий с именем '{}' для хаба с id='{}' уже существует", name, hubId);
                    throw new IllegalArgumentException("Сценарий с именем '" + name + "' для хаба с id='" + hubId + "' уже существует");
                });
    }
}
