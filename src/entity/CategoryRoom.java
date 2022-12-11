package entity;

import lombok.Builder;
import lombok.Data;


@Data
@Builder

public class CategoryRoom {

    int id;
    String kind;

}