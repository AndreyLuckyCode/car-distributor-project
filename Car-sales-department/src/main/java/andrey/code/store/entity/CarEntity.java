package andrey.code.store.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String model;

    Integer price;

    @JsonProperty("is_sold")
    boolean isSold;

    @JsonProperty("is_booked")
    boolean isBooked;

    @ManyToOne
    ManagerEntity manager;

    @OneToOne
    ClientEntity client;

    Date bookingExpirationDate;

    public void setIsSold(boolean isSold) {
        this.isSold = isSold;
    }

    public void setIsBooked(boolean isBooked){
        this.isBooked = isBooked;
    }

    public boolean getIsBooked() {
        return isBooked;
    }
}
