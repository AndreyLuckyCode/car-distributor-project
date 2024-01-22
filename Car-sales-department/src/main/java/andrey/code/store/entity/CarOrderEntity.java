package andrey.code.store.entity;


import andrey.code.store.entity.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "car_order")
public class CarOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    @JsonProperty("attached_info")
    String attachedInfo;

    @Builder.Default
    @JsonProperty("order_date")
    Instant orderDate = Instant.now();

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @ManyToOne
    ManagerEntity manager;
}
