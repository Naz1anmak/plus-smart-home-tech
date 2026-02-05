package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.model.ActionType;
import ru.yandex.practicum.model.Scenario;
import ru.yandex.practicum.repository.ScenarioRepository;
import ru.yandex.practicum.service.evaluator.ConditionEvaluator;
import ru.yandex.practicum.service.executor.ActionExecutor;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScenarioExecutionService {
    private final ScenarioRepository scenarioRepository;
    private final ConditionEvaluator conditionEvaluator;
    private final ActionExecutor actionExecutor;

    @Transactional
    public void process(SensorsSnapshotAvro snapshot) {
        String hubId = snapshot.getHubId();

        List<Scenario> scenarios = scenarioRepository.findByHubId(hubId);

        for (Scenario scenario : scenarios) {
            if (conditionEvaluator.matches(snapshot, scenario)) {
                log.info("Сценарий с id {} выполнен для хаба с id {}", scenario.getId(), hubId);

                scenario.getActions().forEach((sensorId, action) -> {
                    DeviceActionProto grpcAction = actionExecutor.createAction(
                            sensorId,
                            mapActionType(action.getType()),
                            action.getValue()
                    );

                    actionExecutor.executeAction(scenario.getHubId(), scenario.getName(), grpcAction);
                });
            }
        }
    }

    private ActionTypeProto mapActionType(ActionType type) {
        return switch (type) {
            case ACTIVATE -> ActionTypeProto.ACTIVATE;
            case DEACTIVATE -> ActionTypeProto.DEACTIVATE;
            case INVERSE -> ActionTypeProto.INVERSE;
            case SET_VALUE -> ActionTypeProto.SET_VALUE;
        };
    }
}
