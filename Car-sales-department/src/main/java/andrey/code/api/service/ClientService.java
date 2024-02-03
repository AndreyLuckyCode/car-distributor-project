package andrey.code.api.service;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.ClientDTO;
import andrey.code.store.entity.CarEntity;
import andrey.code.store.entity.ClientEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClientService {

    public ClientDTO createClient(
            @ModelAttribute ClientEntity client);

    public List<ClientDTO> getAllClients();

    public ClientDTO updateClient(
            @RequestParam("client_id") Long id,
            @ModelAttribute ClientEntity client);

    public AckDTO deleteClient(
            @PathVariable("client_id") Long id);

    public AckDTO carBooking(
            @PathVariable("client_id") Long id,
            @RequestParam Long carId);
}
