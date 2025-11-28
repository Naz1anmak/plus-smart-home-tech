package ru.yandex.practicum.model.hub.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.HubEventType;
import ru.yandex.practicum.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.service.ScenarioAddedService;

@Component
@RequiredArgsConstructor
public class ScenarioAddedEventHandler implements HubEventHandler {
    private final ScenarioAddedService scenarioAddedService;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }

    @Override
    public void handle(HubEvent event) {
        ScenarioAddedEvent scenarioAddedEvent = (ScenarioAddedEvent) event;
        scenarioAddedService.save(scenarioAddedEvent);
    }
}
