package entity;


import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public class UserInfo {

    int id;
    String firstName;
    String lastName;
    String email;
    int amount;
    String password;
    int roleId;


}