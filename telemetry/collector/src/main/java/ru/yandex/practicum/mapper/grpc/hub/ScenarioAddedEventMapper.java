package ru.yandex.practicum.mapper.grpc.hub;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.model.hub.ScenarioAddedEvent;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ScenarioAddedEventMapper {
    private final ScenarioConditionMapper conditionMapper;
    private final DeviceActionMapper actionMapper;

    public ScenarioAddedEvent fromProto(HubEventProto proto) {
        ScenarioAddedEvent event = new ScenarioAddedEvent();
        ScenarioAddedEventProto scenarioAdded = proto.getScenarioAdded();

        event.setHubId(proto.getHubId());
        event.setTimestamp(Instant.ofEpochSecond(
                proto.getTimestamp().getSeconds(),
                proto.getTimestamp().getNanos()));
        event.setName(scenarioAdded.getName());
        event.setConditions(scenarioAdded.getConditionList().stream()
                .map(conditionMapper::fromProto)
                .toList()
        );
        event.setActions(scenarioAdded.getActionList().stream()
                .map(actionMapper::fromProto)
                .toList()
        );

        return event;
    }
}
