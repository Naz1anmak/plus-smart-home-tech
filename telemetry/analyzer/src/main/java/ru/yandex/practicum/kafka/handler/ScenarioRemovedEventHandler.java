package ru.yandex.practicum.kafka.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.model.HubEventType;
import ru.yandex.practicum.service.ScenarioService;

@Component
@RequiredArgsConstructor
public class ScenarioRemovedEventHandler implements HubEventHandler {
    private final ScenarioService scenarioService;

    @Override
    public HubEventType getEventType() {
        return HubEventType.SCENARIO_REMOVED;
    }

    @Override
    public void handle(HubEventAvro event) {
        ScenarioRemovedEventAvro payload = (ScenarioRemovedEventAvro) event.getPayload();
        scenarioService.removeScenario(event.getHubId(), payload.getName());
    }
}
