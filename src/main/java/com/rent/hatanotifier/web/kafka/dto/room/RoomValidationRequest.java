package com.rent.hatanotifier.web.kafka.dto.room;

import java.util.UUID;

public class RoomValidationRequest {
    private String idRequest;
    private UUID idRoom;
    private UUID idRoomOwner;
    private String action;

    public String getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    public UUID getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(UUID idRoom) {
        this.idRoom = idRoom;
    }

    public UUID getIdRoomOwner() {
        return idRoomOwner;
    }

    public void setIdRoomOwner(UUID idRoomOwner) {
        this.idRoomOwner = idRoomOwner;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
