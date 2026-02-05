package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.mapper.ScenarioMapper;
import ru.yandex.practicum.model.Scenario;
import ru.yandex.practicum.repository.ScenarioRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScenarioService {
    private final ScenarioRepository scenarioRepository;
    private final ScenarioReadService scenarioReadService;
    private final ScenarioMapper scenarioMapper;

    @Transactional
    public void addScenario(String hubId, ScenarioAddedEventAvro payload) {
        scenarioReadService.existsByHubIdAndName(hubId, payload.getName());
        Scenario scenario = scenarioMapper.fromAvro(hubId, payload);
        scenarioRepository.save(scenario);
        log.info("Сценарий '{}' добавлен для хаба {}", payload.getName(), hubId);
    }

    @Transactional
    public void removeScenario(String hubId, String name) {
        Scenario scenario = scenarioReadService.findByHubIdAndName(hubId, name);
        scenarioRepository.delete(scenario);
        log.info("Сценарий '{}' удален для хаба {}", name, hubId);
    }
}
