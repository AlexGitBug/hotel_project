package entity;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder

public class Role {

    int id;
    String rank;

}