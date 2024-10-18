package com.kauan_cancelier.str_producer.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class StringProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        var future = kafkaTemplate.send("str-topic", message);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Error sending message: {}", exception.getMessage());
                return;
            }
            log.info("Message sent successfully: {}", result.getProducerRecord().value());
            log.info("Partition: {}, Offset: {}", result.getRecordMetadata().partition(), result.getRecordMetadata().offset()
            );
        });
    }
}
