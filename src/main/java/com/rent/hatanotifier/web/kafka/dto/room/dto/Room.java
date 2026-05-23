package com.rent.hatanotifier.web.kafka.dto.room.dto;


import com.rent.hatanotifier.web.kafka.dto.room.dto.enums.RoomStatus;

import java.util.UUID;

public class Room {

    private UUID idRoom;
    private UUID idRoomOwner;
    private Integer price;
    private RoomStatus roomStatus;

    public Room() {
    }

    public Room(UUID idRoom,
                UUID idRoomOwner,
                Integer price,
                RoomStatus roomStatus) {
        this.idRoom = idRoom;
        this.idRoomOwner = idRoomOwner;
        this.price = price;
        this.roomStatus = roomStatus;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }
}

