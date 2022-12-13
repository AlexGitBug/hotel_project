package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {

    int id;
    String description;

}
