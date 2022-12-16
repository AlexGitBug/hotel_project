package service;


import dao.RoomDao;
import dto.RoomDto;
import entity.*;
import entity.Enum.NumberRoomEnum;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class RoomService {

    private static final RoomService INSTANCE = new RoomService();

    private final RoomDao roomDao = RoomDao.getInstance();

    public List<RoomDto> findAll() {
        return roomDao.findAll().stream()
                .map(room -> RoomDto.builder()
                        .id(room.getId())
                        .description("""
                               %s - %d - %d - %s
                            """.formatted(room.getNumber(), room.getDayPrice(),
                                room.getFloor(), room.getStatus()))
                        .build())
                .collect(toList());

    }

    public List<RoomDto> findById(int id) {
        return roomDao.findById(id).stream()
                .map(room -> RoomDto.builder()
                        .id(room.getId())
                        .description("""
                            %s - %d - %d - %s
                            """.formatted(room.getNumber(),
                        room.getFloor(), room.getDayPrice(), room.getStatus()))
                        .build())
                .collect(toList());

    }
    public  void save(NumberRoomEnum number, int quantityBed, int categoryRoom, int floor, int dayPrice, RoomStatusEnum status) {
        var qua = QuantityBed.builder()
                .id(quantityBed)
                .build();
        var cat = CategoryRoom.builder()
                .id(categoryRoom)
                .build();
        var room = Room.builder()
                .number(number)
                .quantityBed(qua)
                .categoryRoom(cat)
                .floor(floor)
                .dayPrice(dayPrice)
                .status(status)
                .build();
        roomDao.save(room);

    }

    public boolean delete(int id) {
        return roomDao.delete(id);

    }

    public void update(int id, NumberRoomEnum number, int quantityBed, int categoryRoom, int floor, int dayPrice, RoomStatusEnum status) {
        var roomHotel = roomDao.findById(id);
        var qua = QuantityBed.builder()
                .id(quantityBed)
                .build();
        var cat = CategoryRoom.builder()
                .id(categoryRoom)
                .build();
        roomHotel.ifPresent(room -> {
            room.setNumber(number);
            room.setQuantityBed(qua);
            room.setCategoryRoom(cat);
            room.setFloor(floor);
            room.setDayPrice(dayPrice);
            room.setStatus(status);
            roomDao.update(room);
        });
    }



    public static RoomService getInstance() {
        return INSTANCE;
    }
}
/**
 *
 */