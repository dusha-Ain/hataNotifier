package com.rent.hatanotifier.web.kafka.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class BookingEvent {

    private String eventType;

    private UUID idBooking;

    private UUID idActor;

    private Object data;

    private LocalDateTime timestamp;

    public BookingEvent() {
    }

    public BookingEvent(String eventType,
                        UUID idBooking,
                        UUID idActor,
                        Object data,
                        LocalDateTime timestamp) {
        this.eventType = eventType;
        this.idBooking = idBooking;
        this.idActor = idActor;
        this.data = data;
        this.timestamp = timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public UUID getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(UUID idBooking) {
        this.idBooking = idBooking;
    }

    public UUID getIdActor() {
        return idActor;
    }

    public void setIdActor(UUID idActor) {
        this.idActor = idActor;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
