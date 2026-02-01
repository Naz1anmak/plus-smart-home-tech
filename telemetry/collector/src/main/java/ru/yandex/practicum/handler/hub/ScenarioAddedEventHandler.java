package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.mapper.grpc.hub.ScenarioAddedEventMapper;
import ru.yandex.practicum.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.service.ScenarioAddedService;

@Component
@RequiredArgsConstructor
public class ScenarioAddedEventHandler implements HubEventHandler {
    private final ScenarioAddedService scenarioAddedService;
    private final ScenarioAddedEventMapper mapper;

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }

    @Override
    public void handle(HubEventProto proto) {
        ScenarioAddedEvent scenarioAddedEvent = mapper.fromProto(proto);
        scenarioAddedService.save(scenarioAddedEvent);
    }
}
