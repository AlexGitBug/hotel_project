package dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RoleDto {

    int id;
    String rank;

}