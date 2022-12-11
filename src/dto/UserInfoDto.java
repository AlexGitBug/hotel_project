package dto;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserInfoDto {

    int id;
    String description;
}