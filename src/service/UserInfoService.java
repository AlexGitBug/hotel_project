package service;

import dao.UserInfoDao;
import dto.UserInfoDto;
import entity.UserInfo;

import java.util.List;
import static java.util.stream.Collectors.toList;

    public class UserInfoService {
        private static final UserInfoService INSTANCE = new UserInfoService();
        private final UserInfoDao userInfoDao = UserInfoDao.getInstance();
        public List<UserInfoDto> findAll() {
            return userInfoDao.findAll().stream()
                    .map(userInfo -> UserInfoDto.builder()
                            .id(userInfo.getId())
                            .description("""
                               %s - %s - %s - %d - %s - %d
                            """.formatted(userInfo.getFirstName(), userInfo.getLastName(), userInfo.getEmail(), userInfo.getAmount(),
                                    userInfo.getPassword(), userInfo.getRoleId()))
                            .build())
                    .collect(toList());

        }

        public List<UserInfoDto> findById(int id) {
            return userInfoDao.findById(id).stream()
                    .map(userInfo -> UserInfoDto.builder()
                            .id(userInfo.getId())
                            .description("""
                            %s - %s - %s - %d - %s - %d
                            """.formatted(userInfo.getFirstName(), userInfo.getLastName(), userInfo.getEmail(), userInfo.getAmount(),
                                    userInfo.getPassword(), userInfo.getRoleId()))
                            .build())
                    .collect(toList());

        }

        public  void save(String firstName, String lastName, String email, int amount, String password, int roleId) {
            var userInfo = UserInfo.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .amount(amount)
                    .password(password)
                    .roleId(roleId)
                    .build();
            userInfoDao.save(userInfo);

        }

        public boolean delete(int id) {
            return userInfoDao.delete(id);

        }

        public void update(int id, String firstName, String lastName, String email, int amount, String password, int roleId) {
            var userInfoHotel = userInfoDao.findById(id);
            userInfoHotel.ifPresent(userInfo -> {
                userInfo.setFirstName(firstName);
                userInfo.setLastName(lastName);
                userInfo.setEmail(email);
                userInfo.setAmount(amount);
                userInfo.setPassword(password);
                userInfo.setRoleId(roleId);
                userInfoDao.update(userInfo);
            });
        }

        public static UserInfoService getInstance() {
            return INSTANCE;
        }
    }

