package ru.yandex.practicum.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka.consumer")
public class KafkaConsumerProperties {
    private String bootstrapServers;
    private boolean enableAutoCommit;
    private int pollTimeoutMs;
    private String groupId;
    private Topics topics;

    @Getter
    @Setter
    public static class Topics {
        private String sensors;
    }
}
