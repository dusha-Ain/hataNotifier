package com.rent.hatanotifier.web.kafka.dto.room;


import com.rent.hatanotifier.web.kafka.dto.room.dto.Room;

public class RoomValidationResponse {
    private String idRequest;
    private boolean exists;
    private Room room;
    private String errorMessage;

    public RoomValidationResponse() {
    }

    public String getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
