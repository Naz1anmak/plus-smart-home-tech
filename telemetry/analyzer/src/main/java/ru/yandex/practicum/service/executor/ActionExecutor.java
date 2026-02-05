package ru.yandex.practicum.service.executor;

import com.google.protobuf.Timestamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionExecutor {

    @GrpcClient("hub-router")
    private HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterClient;

    public void executeAction(String hubId, String scenarioName, DeviceActionProto action) {
        DeviceActionRequest request = DeviceActionRequest.newBuilder()
                .setHubId(hubId)
                .setScenarioName(scenarioName)
                .setAction(action)
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(Instant.now().getEpochSecond())
                        .setNanos(Instant.now().getNano())
                        .build())
                .build();

        log.info("Отправляем действие для хаба с id {}: {}", hubId, action);
        hubRouterClient.handleDeviceAction(request);
    }

    public DeviceActionProto createAction(String sensorId, ActionTypeProto type, Integer value) {
        DeviceActionProto.Builder builder = DeviceActionProto.newBuilder()
                .setSensorId(sensorId)
                .setType(type);

        if (value != null) {
            builder.setValue(value);
        }

        return builder.build();
    }
}
