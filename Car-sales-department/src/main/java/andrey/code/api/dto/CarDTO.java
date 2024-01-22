package andrey.code.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarDTO {

    Long id;

    String model;

    Integer price;

    @JsonProperty("is_sold")
    boolean isSold;
}
