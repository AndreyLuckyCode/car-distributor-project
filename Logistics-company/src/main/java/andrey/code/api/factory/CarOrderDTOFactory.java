package andrey.code.api.factory;

import andrey.code.api.dto.CarOrderDTO;
import andrey.code.store.entity.CarOrderEntity;
import org.springframework.stereotype.Component;

@Component
public class CarOrderDTOFactory {

    public CarOrderDTO createCarOrderDTO(CarOrderEntity entity){

        return CarOrderDTO
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .attachedInfo(entity.getAttachedInfo())
                .status(entity.getStatus())
                .build();
    }
}
