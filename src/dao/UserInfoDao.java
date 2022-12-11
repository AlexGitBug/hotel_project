package dao;


import entity.CategoryRoom;
import entity.UserInfo;
import exception.DaoException;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserInfoDao {
    private static final UserInfoDao INSTANCE = new UserInfoDao();


    private static final String FIND_ALL_SQL = """
            SELECT id, first_name, last_name, email, amount, password, role_id
            FROM user_info
            """;

    private static final String DELETE_SQL = """
            DELETE FROM user_info
            WHERE id  = ?
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO user_info (first_name, last_name, email, amount, password, role_id)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE user_info
            SET first_name = ?,
                last_name = ?,
                email = ?,
                amount = ?,
                password = ?,
                role_id = ?
            WHERE id = ?
            """;
    private UserInfoDao() {
    }

    public List<UserInfo> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();

            List<UserInfo> userInfo = new ArrayList<>();
            while (resultSet.next()) {
                userInfo.add(buildUserInfo(resultSet));
            }
            return userInfo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<UserInfo> findById(int id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            UserInfo userInfo = null;
            if (resultSet.next()) {
                userInfo = buildUserInfo(resultSet);
            }
            return Optional.ofNullable(userInfo);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    private UserInfo buildUserInfo(ResultSet resultSet) throws SQLException {
        return UserInfo.builder()
                .id(resultSet.getObject("id", Integer.class))
                .firstName(resultSet.getObject("first_name", String.class))
                .lastName(resultSet.getObject("last_name", String.class))
                .email(resultSet.getObject("email", String.class))
                .amount(resultSet.getObject("amount", Integer.class))
                .password(resultSet.getObject("password", String.class))
                .roleId(resultSet.getObject("role_id", Integer.class))
                .build();

    }

    public UserInfo save(UserInfo userInfo) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, userInfo.getFirstName());
            preparedStatement.setString(2, userInfo.getLastName());
            preparedStatement.setString(3, userInfo.getEmail());
            preparedStatement.setInt(4, userInfo.getAmount());
            preparedStatement.setString(5, userInfo.getPassword());
            preparedStatement.setInt(6, userInfo.getRoleId());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userInfo.setId(generatedKeys.getInt("id"));
            }
            return userInfo;
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

    public void update(UserInfo userInfo) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, userInfo.getFirstName());
            preparedStatement.setString(2, userInfo.getLastName());
            preparedStatement.setString(3, userInfo.getEmail());
            preparedStatement.setInt(4, userInfo.getAmount());
            preparedStatement.setString(5, userInfo.getPassword());
            preparedStatement.setInt(6, userInfo.getRoleId());
            preparedStatement.setInt(7, userInfo.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public static UserInfoDao getInstance() {
        return INSTANCE;
    }
}