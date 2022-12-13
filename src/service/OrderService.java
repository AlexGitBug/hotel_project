package service;

import dao.OrderDao;

import dto.OrderDto;

import entity.Enum.ConditionEnum;
import entity.Order;
import entity.Room;
import entity.UserInfo;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class OrderService {
    private static final OrderService INSTANCE = new OrderService();
    private final OrderDao orderDao = OrderDao.getInstance();

    public List<OrderDto> findAll() {
        return orderDao.findAll().stream()
                .map(order -> OrderDto.builder()
                        .id(order.getId())
                        .description("""
                                   %s - %s - %s - %s - %s - %s
                                """.formatted(order.getUserInfoId().getId(), order.getRoomId().getId(), order.getBeginTimeOfTheOrder(), order.getEndTimeOfTheOrder(),
                                order.getCondition(), order.getMessage()))
                        .build())
                .collect(toList());

    }

    public List<OrderDto> findById(int id) {
        return orderDao.findById(id).stream()
                .map(order -> OrderDto.builder()
                        .id(order.getId())
                        .description("""
                                   %s - %s - %s - %s - %s - %s
                                """.formatted(order.getUserInfoId().getId(), order.getRoomId().getId(), order.getBeginTimeOfTheOrder(), order.getEndTimeOfTheOrder(),
                                order.getCondition(), order.getMessage()))
                        .build())
                .collect(toList());

    }

    public void update(int id, int userId, int roomId, LocalDateTime beginTime, LocalDateTime endTime,
                       ConditionEnum condition, String message) {
        var orderHotel = orderDao.findById(id);
        var user = UserInfo.builder()
                .id(userId)
                .build();
        var room = Room.builder()
                .id(roomId)
                .build();
        orderHotel.ifPresent(order -> {
            order.setUserInfoId(user);
            order.setRoomId(room);
            order.setBeginTimeOfTheOrder(beginTime);
            order.setEndTimeOfTheOrder(endTime);
            order.setCondition(condition);
            order.setMessage(message);
            orderDao.update(order);

        });
    }

    public void save(int userId, int roomId, LocalDateTime beginTime, LocalDateTime endTime, ConditionEnum condition, String message) {

        var user = UserInfo.builder()
                .id(userId)
                .build();
        var room = Room.builder()
                .id(roomId)
                .build();
        var order = Order.builder()
                .userInfoId(user)
                .roomId(room)
                .beginTimeOfTheOrder(beginTime)
                .endTimeOfTheOrder(endTime)
                .condition(condition)
                .message(message)
                .build();
        orderDao.save(order);

    }
    public boolean delete(int id) {
        return orderDao.delete(id);

    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
