package andrey.code.api.dto;

import andrey.code.store.entity.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarOrderDTO {

    Long id;

    String title;

    @JsonProperty("attached_info")
    String attachedInfo;

    @JsonProperty("order_date")
    Instant orderDate = Instant.now();

    @Enumerated(EnumType.STRING)
    OrderStatus status;
}
