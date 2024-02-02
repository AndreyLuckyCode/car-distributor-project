package andrey.code.api.factory;

import andrey.code.api.dto.LogistDTO;
import andrey.code.store.entity.LogistEntity;
import org.springframework.stereotype.Component;

@Component
public class LogistDTOFactory {

    public LogistDTO createLogistDTO (LogistEntity entity){

        return LogistDTO
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
}
