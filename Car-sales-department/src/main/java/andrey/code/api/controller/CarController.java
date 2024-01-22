package andrey.code.api.controller;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.CarDTO;
import andrey.code.api.service.CarService;
import andrey.code.store.entity.CarEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class CarController {

    CarService carService;

    private static final String CREATE_CAR = "/api/cars";
    private static final String GET_ALL_CARS_BY_MANAGER_ID = "/api/managers/{manager_id}/cars";
    private static final String GET_NON_SOLD_CARS = "/api/cars/non_sold";
    private static final String GET_SOLD_CARS = "/api/cars/sold";
    private static final String UPDATE_OR_SALE_CAR = "/api/cars/{car_id}";
    private static final String DELETE_CAR = "/api/cars/{car_id}";


    @PostMapping(CREATE_CAR)
    public CarDTO createCar(@ModelAttribute CarEntity car){

        return carService.createCar(car);
    }


    @GetMapping(GET_ALL_CARS_BY_MANAGER_ID)
    public List<CarDTO> getAllCarsByManagerId(
            @PathVariable("manager_id") Long id){

        return carService.getAllCarsByManagerId(id);
    }


    @GetMapping(GET_NON_SOLD_CARS)
    public List<CarDTO> getNonSoldCars(){

        return carService.getNonSoldCars();
    }


    @GetMapping(GET_SOLD_CARS)
    public List<CarDTO> getSoldCars(){

        return carService.getSoldCars();
    }


    @PatchMapping(UPDATE_OR_SALE_CAR)
    public CarDTO updateOrSaleCar(
            @PathVariable("car_id") Long id,
            @RequestParam(value = "manager_id", required = false) Long managerId,
            @ModelAttribute CarEntity car){

        return carService.updateOrSellCar(id, managerId, car);
    }


    @DeleteMapping(DELETE_CAR)
    public AckDTO deleteCar(@PathVariable("car_id") Long id){

        return carService.deleteCar(id);
    }
}
