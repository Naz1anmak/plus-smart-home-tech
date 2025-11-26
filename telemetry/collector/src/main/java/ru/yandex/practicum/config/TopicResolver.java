package ru.yandex.practicum.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TopicResolver {
    private final TopicProperties props;

    public String resolve(Enum<?> eventType) {
        String key = eventType.name();
        String topic = props.getMapping().get(key);
        if (topic == null) {
            throw new IllegalArgumentException("No topic mapping found for event type: " + key);
        }
        return topic;
    }
}
