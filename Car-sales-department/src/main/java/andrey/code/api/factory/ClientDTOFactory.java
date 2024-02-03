package andrey.code.api.factory;

import andrey.code.api.dto.ClientDTO;
import andrey.code.store.entity.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientDTOFactory {

    public ClientDTO createClientDTO(ClientEntity entity){

        return ClientDTO
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
}
