package andrey.code.api.service.impl;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.CarToBeDeliveredDTO;
import andrey.code.api.exceptions.BadRequestException;
import andrey.code.api.exceptions.NotFoundException;
import andrey.code.api.factory.CarToBeDeliveredDTOFactory;
import andrey.code.api.service.CarToBeDeliveredService;
import andrey.code.store.entity.CarToBeDeliveredEntity;
import andrey.code.store.entity.LogistEntity;
import andrey.code.store.repository.CarToBeDeliveredRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarToBeDeliveredServiceImpl implements CarToBeDeliveredService {

    CarToBeDeliveredRepository carToBeDeliveredRepository;
    CarToBeDeliveredDTOFactory carToBeDeliveredDTOFactory;

    @Override
    @Transactional
    public CarToBeDeliveredDTO createCarToBeDelivered(
            @ModelAttribute CarToBeDeliveredEntity carToBeDelivered) {

        if(carToBeDelivered.getModel() == null || carToBeDelivered.getModel().trim().isEmpty()){
            throw new BadRequestException("Car to be delivered model can't be empty");
        }
        if(carToBeDelivered.getPrice() == null){
            throw new BadRequestException("Car to be delivered price can't be empty");
        }

        carToBeDeliveredRepository.saveAndFlush(carToBeDelivered);

        return carToBeDeliveredDTOFactory.createCarToBeDeliveredDTO(carToBeDelivered);
    }


    @Override
    @Transactional
    public List<CarToBeDeliveredDTO> getAllCarsToBeDelivered() {

        List<CarToBeDeliveredEntity> carsToBeDelivered = carToBeDeliveredRepository.findAll();

        if(carsToBeDelivered.isEmpty()){
            throw new NotFoundException("Cars to be delivered list is empty");
        }

        return carsToBeDelivered.stream()
                .map(carToBeDeliveredDTOFactory::createCarToBeDeliveredDTO)
                .toList();
    }


    @Override
    @Transactional
    public CarToBeDeliveredDTO updateCarToBeDelivered(
            @PathVariable("car_to_be_delivered_id") Long id,
            @ModelAttribute CarToBeDeliveredEntity carToBeDelivered) {

        CarToBeDeliveredEntity carToBeDeliveredEntity = carToBeDeliveredRepository.findById(id).orElseThrow(()->
                new NotFoundException("Car to be delivered with this id doesn't exist"));

        if(carToBeDelivered.getModel() != null && !carToBeDelivered.getModel().trim().isEmpty()){
            carToBeDeliveredEntity.setModel(carToBeDelivered.getModel());
        }
        if(carToBeDelivered.getPrice() != null){
            carToBeDeliveredEntity.setPrice(carToBeDelivered.getPrice());
        }

        carToBeDeliveredRepository.saveAndFlush(carToBeDeliveredEntity);

        return carToBeDeliveredDTOFactory.createCarToBeDeliveredDTO(carToBeDeliveredEntity);
    }



    @Override
    @Transactional
    public AckDTO deleteCarToBeDelivered(
            @PathVariable("car_to_be_delivered_id") Long id) {

        if(carToBeDeliveredRepository.findById(id).isEmpty()){
            throw new NotFoundException("Car to be delivered with this id doesn't exist");
        }

        carToBeDeliveredRepository.deleteById(id);

        return AckDTO.builder().answer(true).build();
    }
}
