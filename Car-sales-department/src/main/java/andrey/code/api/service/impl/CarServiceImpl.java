package andrey.code.api.service.impl;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.CarDTO;
import andrey.code.api.exceptions.BadRequestException;
import andrey.code.api.exceptions.NotFoundException;
import andrey.code.api.factory.CarDTOFactory;
import andrey.code.api.service.CarService;
import andrey.code.store.entity.CarEntity;
import andrey.code.store.entity.ManagerEntity;
import andrey.code.store.repository.CarRepository;
import andrey.code.store.repository.ManagerRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    CarRepository carRepository;
    CarDTOFactory carDTOFactory;
    ManagerRepository managerRepository;


    @Override
    @Transactional
    public CarDTO createCar(
            @ModelAttribute CarEntity car) {


        if(car.getModel() == null || car.getModel().trim().isEmpty()){
            throw new BadRequestException("Car model can't be empty");
        }
        if(car.getPrice() == null){
            throw new BadRequestException("Car price can't be empty");
        }

        carRepository.saveAndFlush(car);

        return carDTOFactory.createNonSoldCarDTO(car);
    }


    @Override
    @Transactional
    public List<CarDTO> getAllCarsByManagerId(
            @PathVariable("manager_id") Long id) {

        ManagerEntity manager = managerRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Manager with this id doesn't exist"));

        List<CarEntity> cars = carRepository.findAllByManagerId(id);

        if(cars.isEmpty()){
            throw new NotFoundException("This manager didn't sell any cars");
        }

        return cars.stream()
                .map(carDTOFactory::createSoldCarDTO)
                .toList();
    }


    @Override
    @Transactional
    public List<CarDTO> getNonSoldCars() {

        List<CarEntity> nonSoldCars = carRepository.findAllByIsSoldFalse();

        if (nonSoldCars.isEmpty()) {
            throw new NotFoundException("No non-sold cars found");
        }

        return nonSoldCars.stream()
                .map(carDTOFactory::createNonSoldCarDTO)
                .toList();
    }


    @Override
    @Transactional
    public List<CarDTO> getSoldCars() {

        List<CarEntity> soldCars = carRepository.findAllByIsSoldTrue();

        if (soldCars.isEmpty()) {
            throw new NotFoundException("No sold cars found");
        }

        return soldCars.stream()
                .map(carDTOFactory::createSoldCarDTO)
                .toList();
    }


    @Override
    @Transactional
    public CarDTO updateOrSellCar(
            @PathVariable("car_id") Long id,
            @RequestParam(value = "manager_id", required = false) Long managerId,
            @ModelAttribute CarEntity car) {

        CarEntity carEntity = carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Car with this id doesn't exist"));


        if (managerId != null) {
            ManagerEntity manager = managerRepository.findById(managerId)
                    .orElseThrow(() -> new NotFoundException("Manager with this id doesn't exist"));

            carEntity.setManager(manager);
            carEntity.setIsSold(true);

            if(car.getPrice() != null){
                carEntity.setPrice(car.getPrice());
            }

            carRepository.saveAndFlush(carEntity);

            manager.setTotalSales(manager.getTotalSales() + carEntity.getPrice());

            managerRepository.saveAndFlush(manager);

            return carDTOFactory.createSoldCarDTO(carEntity);


        } else {


            if(car.getPrice() != null){
                carEntity.setPrice(car.getPrice());
            }

            if(car.getModel() != null && !car.getModel().trim().isEmpty()){
                carEntity.setModel(car.getModel());
            }

            carRepository.saveAndFlush(carEntity);

            return carDTOFactory.createNonSoldCarDTO(carEntity);
        }
    }


    @Override
    @Transactional
    public AckDTO deleteCar(
            @PathVariable("car_id") Long id) {

        if(carRepository.findById(id).isEmpty()){
            throw new NotFoundException("Car with that ID doesn't exist");
        }

        carRepository.deleteById(id);

        return AckDTO.builder().answer(true).build();
    }
}
