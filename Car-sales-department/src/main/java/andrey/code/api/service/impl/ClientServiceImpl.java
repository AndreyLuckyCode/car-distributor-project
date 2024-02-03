package andrey.code.api.service.impl;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.ClientDTO;
import andrey.code.api.exceptions.BadRequestException;
import andrey.code.api.exceptions.NotFoundException;
import andrey.code.api.factory.ClientDTOFactory;
import andrey.code.api.service.ClientService;
import andrey.code.store.entity.CarEntity;
import andrey.code.store.entity.ClientEntity;
import andrey.code.store.repository.CarRepository;
import andrey.code.store.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ClientServiceImpl implements ClientService {

    CarRepository carRepository;
    ClientRepository clientRepository;
    ClientDTOFactory clientDTOFactory;

    @Override
    @Transactional
    public ClientDTO createClient(
            @ModelAttribute ClientEntity client) {

        if(client.getName() == null || client.getName().trim().isEmpty()){
            throw new BadRequestException("Client name can't be empty");
        }
        if(client.getEmail() == null || client.getEmail().trim().isEmpty()){
            throw new BadRequestException("Client email can't be empty");
        }

        clientRepository.saveAndFlush(client);

        return clientDTOFactory.createClientDTO(client);
    }


    @Override
    @Transactional
    public List<ClientDTO> getAllClients() {

        List<ClientEntity> clients = clientRepository.findAll();

        if(clients.isEmpty()){
            throw new NotFoundException("Clients list is empty");
        }

        return clients.stream()
                .map(clientDTOFactory::createClientDTO)
                .toList();
    }


    @Override
    @Transactional
    public ClientDTO updateClient(
            @PathVariable("client_id") Long id,
            @ModelAttribute ClientEntity client) {

        ClientEntity clientEntity = clientRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Client with this id doesn't exist"));

        if(client.getName() != null && !client.getName().trim().isEmpty()){
            clientEntity.setName(client.getName());
        }
        if(client.getEmail() != null && !client.getEmail().trim().isEmpty()){
            clientEntity.setEmail(client.getEmail());
        }

        clientRepository.saveAndFlush(clientEntity);

        return clientDTOFactory.createClientDTO(clientEntity);
    }


    @Override
    @Transactional
    public AckDTO deleteClient(
            @PathVariable("client_id") Long id) {

        if(clientRepository.findById(id).isEmpty()){
            throw new NotFoundException("Client with this id doesn't exist");
        }

        clientRepository.deleteById(id);

        return AckDTO.builder().answer(true).build();
    }

    @Override
    @Transactional
    public AckDTO carBooking(
            @PathVariable("client_id") Long id,
            @RequestParam Long carId) {

        ClientEntity client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with this id doesn't exist"));

        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new NotFoundException("Car with this id doesn't exist"));

        car.setClient(client);
        car.setIsBooked(true);
        client.setCar(car);

        Date bookingExpirationDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
        car.setBookingExpirationDate(bookingExpirationDate);

        carRepository.saveAndFlush(car);
        clientRepository.saveAndFlush(client);

        return AckDTO.builder().answer(true).build();
    }


    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000) // Проверка каждые 24 часа
    public void checkBookingExpiration() {
        Date currentDate = new Date();
        List<CarEntity> bookedCars = carRepository.findByIsBookedTrueAndBookingExpirationDateBefore(currentDate);
        for (CarEntity car : bookedCars) {

            car.setIsBooked(false);
            car.setClient(null);
            car.setBookingExpirationDate(null);
            carRepository.save(car);

            ClientEntity client = car.getClient();
            if (client != null) {
                client.setCar(null);
                clientRepository.save(client);
            }
        }
    }
}
