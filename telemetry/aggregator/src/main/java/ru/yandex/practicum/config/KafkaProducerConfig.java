package ru.yandex.practicum.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.serializer.GeneralAvroSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaProducerConfig implements DisposableBean {
    private final String bootstrapServers;
    private KafkaProducer<String, SpecificRecordBase> producer;

    public KafkaProducerConfig(@Value("${kafka.producer.bootstrap-servers}") String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    @Bean
    public KafkaProducer<String, SpecificRecordBase> kafkaProducer() {
        if (producer == null) {
            Map<String, Object> props = new HashMap<>();

            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GeneralAvroSerializer.class);

            producer = new KafkaProducer<>(props);
        }
        return producer;
    }

    @Override
    public void destroy() {
        if (producer == null) return;
        try {
            producer.flush();
        } catch (Exception exception) {
            log.warn("Ошибка при flush kafka producer", exception);
        }
        try {
            producer.close(Duration.ofSeconds(10));
        } catch (Exception exception) {
            log.warn("Ошибка при закрытии kafka producer", exception);
        }
    }
}
