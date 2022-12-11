package dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public class RoomDto {

    int id;
    String description;
}
