package ru.yandex.practicum.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.exception.TypeNotFoundException;

@Component
@RequiredArgsConstructor
public class TopicResolver {
    private final TopicProperties props;

    public String resolve(Enum<?> eventType) {
        String key = eventType.name();
        String topic = props.getMapping().get(key);
        if (topic == null) {
            throw new TypeNotFoundException("Неизвестный тип события: " + key);
        }
        return topic;
    }
}
