package ru.yandex.practicum.mapper.grpc.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioRemovedEventProto;
import ru.yandex.practicum.model.hub.ScenarioRemovedEvent;

import java.time.Instant;

@Component
public class ScenarioRemovedEventMapper {

    public ScenarioRemovedEvent fromProto(HubEventProto proto) {
        ScenarioRemovedEvent event = new ScenarioRemovedEvent();
        ScenarioRemovedEventProto scenarioRemoved = proto.getScenarioRemoved();

        event.setHubId(proto.getHubId());
        event.setTimestamp(Instant.ofEpochSecond(
                proto.getTimestamp().getSeconds(),
                proto.getTimestamp().getNanos()));
        event.setName(scenarioRemoved.getName());

        return event;
    }
}
