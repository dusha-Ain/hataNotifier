package com.rent.hatanotifier.web.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rent.hatanotifier.dto.Notifier;
import com.rent.hatanotifier.service.NotifierService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class BookingEventConsumer {

    private final Logger logger = LoggerFactory.getLogger(ProfileEventConsumer.class);
    private final NotifierService notifierService;
    private final ObjectMapper objectMapper;

    @Autowired
    public BookingEventConsumer(NotifierService notifierService, ObjectMapper objectMapper) {
        this.notifierService = notifierService;
        this.objectMapper = objectMapper;
    }

    // Слушаем события бронирований
    @KafkaListener(topics = "booking-events", groupId = "notifier-microservice")
    public void consumeBookingEvent(ConsumerRecord<String, String> record) {
        logger.info("Received booking event: key={}, value={}", record.key(), record.value());

        try {
            JsonNode event = objectMapper.readTree(record.value());
            String eventType = event.get("eventType").asText();
            String aggregateId = event.get("idBooking").asText();
            Notifier notifier = getNotifier(event, aggregateId, eventType);
            notifierService.saveEvent(notifier);
            logger.info("Saved outbox event: type={}, aggregateId={}", eventType, aggregateId);

        } catch (Exception e) {
            logger.error("Failed to process booking event: {}", record.value(), e);
        }
    }

    private @NonNull Notifier getNotifier(JsonNode event, String aggregateId, String eventType) {
        return new Notifier(
                null,
                aggregateId,
                eventType,
                event,
                null,
                null
        );
    }
}