package ru.yandex.practicum.model.hub.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.mapper.grpc.hub.ScenarioRemovedEventMapper;
import ru.yandex.practicum.model.hub.ScenarioRemovedEvent;
import ru.yandex.practicum.service.ScenarioRemovedService;

@Component
@RequiredArgsConstructor
public class ScenarioRemovedEventHandler implements HubEventHandler {
    private final ScenarioRemovedService scenarioRemovedService;
    private final ScenarioRemovedEventMapper mapper;

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_REMOVED;
    }

    @Override
    public void handle(HubEventProto proto) {
        ScenarioRemovedEvent scenarioRemovedEvent = mapper.fromProto(proto);
        scenarioRemovedService.save(scenarioRemovedEvent);
    }
}
