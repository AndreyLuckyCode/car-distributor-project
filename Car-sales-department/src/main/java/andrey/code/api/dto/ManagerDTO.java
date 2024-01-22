package andrey.code.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerDTO {

    Long id;

    String name;

    String email;

    Integer salary;

    @JsonProperty("total_sales")
    Integer totalSales;
}
