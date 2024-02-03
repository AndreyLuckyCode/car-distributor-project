package andrey.code.api.controller;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.ClientDTO;
import andrey.code.api.service.ClientService;
import andrey.code.store.entity.ClientEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientController {

    ClientService clientService;

    private static final String CREATE_CLIENT = "/api/clients";
    private static final String GET_ALL_CLIENTS = "/api/clients";
    private static final String UPDATE_CLIENTS = "/api/clients/{client_id}";
    private static final String DELETE_CLIENT = "/api/clients/{client_id}";


    @PostMapping(CREATE_CLIENT)
    public ClientDTO createClient(
            @ModelAttribute ClientEntity client){

        return clientService.createClient(client);
    }

    @GetMapping(GET_ALL_CLIENTS)
    public List<ClientDTO> getAllClients(){

        return clientService.getAllClients();
    }

    @PatchMapping(UPDATE_CLIENTS)
    public ClientDTO updateClient(
            @PathVariable("client_id") Long id,
            @ModelAttribute ClientEntity client){

        return clientService.updateClient(id, client);
    }

    @DeleteMapping(DELETE_CLIENT)
    public AckDTO deleteClient(
            @PathVariable("client_id") Long id){

        return clientService.deleteClient(id);
    }

}
