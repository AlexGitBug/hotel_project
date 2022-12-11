package entity;

import lombok.Builder;

import lombok.Data;

@Data
@Builder
public class Room {

    int id;
    NumberRoom number;
    QuantityBed quantityBed;
    CategoryRoom categoryRoom;
    int floor;
    int dayPrice;
    RoomStatusEnum status;

}
