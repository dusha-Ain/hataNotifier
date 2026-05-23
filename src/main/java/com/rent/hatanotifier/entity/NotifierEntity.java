package com.rent.hatanotifier.entity;



import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Timestamp;
import java.util.UUID;

public class NotifierEntity {

    private UUID id;

    private String aggregateId;

    private String type;

    private JsonNode payload;

    private Timestamp createdAt;

    private Timestamp processedAt;

    public NotifierEntity() {
    }

    public NotifierEntity(UUID id,
                          String aggregateId,
                          String type,
                          JsonNode payload,
                          Timestamp createdAt,
                          Timestamp processedAt) {
        this.id = id;
        this.aggregateId = aggregateId;
        this.type = type;
        this.payload = payload;
        this.createdAt = createdAt;
        this.processedAt = processedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JsonNode getPayload() {
        return payload;
    }

    public void setPayload(JsonNode payload) {
        this.payload = payload;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Timestamp processedAt) {
        this.processedAt = processedAt;
    }
}
