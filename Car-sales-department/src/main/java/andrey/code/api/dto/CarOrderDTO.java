package andrey.code.api.dto;

import andrey.code.store.entity.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarOrderDTO implements Serializable {

    Long id;

    String title;

    @JsonProperty("attached_info")
    String attachedInfo;

    @Enumerated(EnumType.STRING)
    OrderStatus status;
}
