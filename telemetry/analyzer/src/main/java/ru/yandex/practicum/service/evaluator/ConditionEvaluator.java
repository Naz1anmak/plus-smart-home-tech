package ru.yandex.practicum.service.evaluator;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.ConflictException;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.model.Condition;
import ru.yandex.practicum.model.ConditionType;
import ru.yandex.practicum.model.Scenario;

@Component
public class ConditionEvaluator {

    public boolean matches(SensorsSnapshotAvro snapshot, Scenario scenario) {
        return scenario.getConditions().entrySet().stream()
                .allMatch(entry ->
                        evaluate(snapshot, entry.getKey(), entry.getValue())
                );
    }

    private boolean evaluate(
            SensorsSnapshotAvro snapshot,
            String sensorId,
            Condition condition
    ) {
        SensorStateAvro state = snapshot.getSensorsState().get(sensorId);
        if (state == null) {
            return false;
        }

        int actual = extractValue(state, condition.getType());

        return switch (condition.getOperation()) {
            case GREATER_THAN -> actual > condition.getValue();
            case LOWER_THAN -> actual < condition.getValue();
            case EQUALS -> actual == condition.getValue();
        };
    }

    private int extractValue(SensorStateAvro state, ConditionType type) {
        Object data = state.getData();

        return switch (type) {

            case TEMPERATURE -> {
                if (data instanceof TemperatureSensorAvro t) {
                    yield t.getTemperatureC();
                }
                if (data instanceof ClimateSensorAvro c) {
                    yield c.getTemperatureC();
                }
                throw new ConflictException("Температура недоступна для типа: " + data.getClass());
            }

            case HUMIDITY -> {
                if (data instanceof ClimateSensorAvro c) {
                    yield c.getHumidity();
                }
                throw new ConflictException("Влажность недоступна для типа: " + data.getClass());
            }

            case CO2LEVEL -> {
                if (data instanceof ClimateSensorAvro c) {
                    yield c.getCo2Level();
                }
                throw new ConflictException("CO2 недоступен для типа: " + data.getClass());
            }

            case MOTION -> {
                if (data instanceof MotionSensorAvro m) {
                    yield m.getMotion() ? 1 : 0;
                }
                throw new ConflictException("Motion недоступен для типа: " + data.getClass());
            }

            case SWITCH -> {
                if (data instanceof SwitchSensorAvro s) {
                    yield s.getState() ? 1 : 0;
                }
                throw new ConflictException("Switch недоступен для типа: " + data.getClass());
            }

            case LUMINOSITY -> {
                if (data instanceof LightSensorAvro l) {
                    yield l.getLuminosity();
                }
                throw new ConflictException("Luminosity недоступна для типа: " + data.getClass());
            }
        };
    }
}
