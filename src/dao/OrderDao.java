package dao;

import entity.Enum.ConditionEnum;
import entity.Enum.NumberRoomEnum;
import entity.Order;
import entity.Room;
import entity.RoomStatusEnum;
import entity.UserInfo;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class OrderDao {

        private static final dao.OrderDao INSTANCE = new dao.OrderDao();
        private static final String FIND_ALL_SQL = """
            SELECT orders.id,
                   user_info_id,
                  begin_time,
                   end_time,
                  condition, 
                  message,
                  ui.first_name,
                  ui.last_name,
                  ui.email,
                  ui.amount,
                  ui.password,
                  ui.role_id,
                  r.number_room,
                  r.quantity_bed_id,
                  r.category_room_id,
                  r.floor,
                  r.day_price,
                  r.status
                FROM orders
                JOIN user_info ui ON ui.id = orders.user_info_id
                JOIN room r on orders.room_id = r.id
            """;

        private static final String UPDATE_SQL = """
            UPDATE orders
            SET begin_time = ?,
                end_time = ?,
                condition = ?,
                message = ?
            WHERE id = ?
            """;
        private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE orders.id = ?
            """;

        private static final String SAVE_SQL = """
            INSERT INTO orders (user_info_id, room_id, begin_time, end_time, condition, message)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String DELETE_SQL = """
            DELETE FROM orders
            WHERE id  = ?
            """;
        private OrderDao() {
        }

        public List<Order> findAll() {
            try (var connection = ConnectionManager.get();
                 var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
                var resultSet = preparedStatement.executeQuery();
                List<Order> orders = new ArrayList<>();
                while (resultSet.next()) {
                    orders.add(buildOrder(resultSet));
                }
                return orders;
            } catch (SQLException throwables) {
                throw new DaoException(throwables);
            }
        }

        public Optional<Order> findById(int id) {
            try (var connection = ConnectionManager.get();
                 var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
                preparedStatement.setInt(1, id);

                var resultSet = preparedStatement.executeQuery();
                Order order = null;
                if (resultSet.next()) {
                    order = buildOrder(resultSet);
                }
                return Optional.ofNullable(order);
            } catch (SQLException throwables) {
                throw new DaoException(throwables);
            }
        }

        private static Order buildOrder(ResultSet resultSet) throws SQLException {
            var userInfo = UserInfo.builder()
                    .id(resultSet.getInt("user_info_id"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .email(resultSet.getString("email"))
                    .amount(resultSet.getInt("amount"))
                    .password(resultSet.getString("password"))
                    .roleId(resultSet.getInt("role_id"))
                    .build();

            var room = Room.builder()
                    .id(resultSet.getInt("id"))
                    .number(NumberRoomEnum.valueOf(resultSet.getObject("number_room", String.class)))
                    .floor(resultSet.getInt("floor"))
                    .dayPrice(resultSet.getInt("day_price"))
                    .status(RoomStatusEnum.valueOf(resultSet.getObject("status", String.class)))
                    .build();

            return Order.builder()
                    .id(resultSet.getInt("id"))
                    .userInfoId(userInfo)
                    .roomId(room)
                    .beginTimeOfTheOrder(resultSet.getTimestamp("begin_time").toLocalDateTime())
                    .endTimeOfTheOrder(resultSet.getTimestamp("end_time").toLocalDateTime())
                    .condition(ConditionEnum.valueOf(resultSet.getObject("condition", String.class)))
                    .message(resultSet.getObject("message", String.class))
                    .build();

        }

        public void update(Order order) {
            try (var connection = ConnectionManager.get();
                 var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
//                preparedStatement.setInt(1, order.getUserInfoId().getId());
//                preparedStatement.setInt(2, order.getRoomId().getId());
                preparedStatement.setTimestamp(1, Timestamp.valueOf(order.getBeginTimeOfTheOrder()));
                preparedStatement.setTimestamp(2, Timestamp.valueOf(order.getEndTimeOfTheOrder()));
                preparedStatement.setString(3, order.getCondition().toString());
                preparedStatement.setString(4, order.getMessage());
                preparedStatement.setInt(5, order.getId());

                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throw new DaoException(throwables);
            }
        }
        public Order save(Order order) {
            try (var connection = ConnectionManager.get();
                 var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
//                preparedStatement.setInt(1, order.getUserInfoId().getId());
//                preparedStatement.setInt(2, order.getRoomId().getId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(order.getBeginTimeOfTheOrder()));
                preparedStatement.setTimestamp(4, Timestamp.valueOf(order.getEndTimeOfTheOrder()));
                preparedStatement.setString(5, order.getCondition().toString());
                preparedStatement.setString(6, order.getMessage());

                preparedStatement.executeUpdate();

                var generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt("id"));
                }
                return order;
            } catch (SQLException throwables) {
                throw new DaoException(throwables);
            }
        }

    public boolean delete(int id) {
        try (Connection connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }



        public static dao.OrderDao getInstance() {
            return INSTANCE;
        }
    }

