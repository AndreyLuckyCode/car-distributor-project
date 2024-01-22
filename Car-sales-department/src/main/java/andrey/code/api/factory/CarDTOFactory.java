package andrey.code.api.factory;

import andrey.code.api.dto.CarDTO;
import andrey.code.store.entity.CarEntity;
import org.springframework.stereotype.Component;

@Component
public class CarDTOFactory {

    public CarDTO createNonSoldCarDTO(CarEntity entity){

        return CarDTO
                .builder()
                .id(entity.getId())
                .model(entity.getModel())
                .price(entity.getPrice())
                .isSold(false)
                .build();
    }

    public CarDTO createSoldCarDTO(CarEntity entity){

        return CarDTO
                .builder()
                .id(entity.getId())
                .model(entity.getModel())
                .price(entity.getPrice())
                .isSold(true)
                .build();
    }
}
