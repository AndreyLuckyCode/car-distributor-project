package andrey.code.api.factory;

import andrey.code.api.dto.CarToBeDeliveredDTO;
import andrey.code.store.entity.CarToBeDeliveredEntity;
import org.springframework.stereotype.Component;

@Component
public class CarToBeDeliveredDTOFactory {

    public CarToBeDeliveredDTO createCarToBeDeliveredDTO(CarToBeDeliveredEntity entity){

        return CarToBeDeliveredDTO
                .builder()
                .id(entity.getId())
                .model(entity.getModel())
                .price(entity.getPrice())
                .build();
    }
}
