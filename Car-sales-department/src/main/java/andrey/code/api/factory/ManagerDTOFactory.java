package andrey.code.api.factory;

import andrey.code.api.dto.ManagerDTO;
import andrey.code.store.entity.ManagerEntity;
import org.springframework.stereotype.Component;

@Component
public class ManagerDTOFactory {

    public ManagerDTO createManagerDTO(ManagerEntity entity){

        return ManagerDTO
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .salary(entity.getSalary())
                .totalSales(entity.getTotalSales())
                .build();
    }
}
