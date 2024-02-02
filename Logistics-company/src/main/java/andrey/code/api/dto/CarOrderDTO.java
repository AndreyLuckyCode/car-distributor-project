package andrey.code.api.dto;

import andrey.code.store.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarOrderDTO {

    Long id;

    String title;

    @JsonProperty("attached_info")
    String attachedInfo;

    @Enumerated(EnumType.STRING)
    OrderStatus status;
}
