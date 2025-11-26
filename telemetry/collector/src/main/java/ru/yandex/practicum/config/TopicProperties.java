package ru.yandex.practicum.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka.topics")
public class TopicProperties {
    private String sensors;
    private String hubs;
    private Map<String, String> mapping = new HashMap<>();
}
