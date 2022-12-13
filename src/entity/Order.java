package entity;

import entity.Enum.ConditionEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class Order {
    int id;
    UserInfo userInfoId;
    Room roomId;
    LocalDateTime beginTimeOfTheOrder;
    LocalDateTime endTimeOfTheOrder;
    ConditionEnum condition;
    String message;
}