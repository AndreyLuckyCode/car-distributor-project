package andrey.code.api.controller;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.CarToBeDeliveredDTO;
import andrey.code.api.service.CarToBeDeliveredService;
import andrey.code.store.entity.CarToBeDeliveredEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarToBeDeliveredController {

    CarToBeDeliveredService carToBeDeliveredService;

    private static final String CREATE_CAR_TO_BE_DELIVERED = "/api/cars";
    private static final String GET_ALL_CARS_TO_BE_DELIVERED = "/api/cars";
    private static final String UPDATE_CAR_TO_BE_DELIVERED = "/api/cars/{car_to_be_delivered}";
    private static final String DELETE_CAR_TO_BE_DELIVERED = "/api/cars/{car_to_be_delivered}";


    @PostMapping(CREATE_CAR_TO_BE_DELIVERED)
    public CarToBeDeliveredDTO createCarToBeDelivered(
            @ModelAttribute CarToBeDeliveredEntity carToBeDelivered){

        return carToBeDeliveredService.createCarToBeDelivered(carToBeDelivered);
    }

    @GetMapping(GET_ALL_CARS_TO_BE_DELIVERED)
    public List<CarToBeDeliveredDTO> getAllCarsToBeDelivered(){

        return carToBeDeliveredService.getAllCarsToBeDelivered();
    }

    @PatchMapping(UPDATE_CAR_TO_BE_DELIVERED)
    public CarToBeDeliveredDTO updateCarToBeDelivered(
            @PathVariable("car_to_be_delivered") Long id,
            @ModelAttribute CarToBeDeliveredEntity carToBeDelivered){

        return carToBeDeliveredService.updateCarToBeDelivered(id, carToBeDelivered);
    }

    @DeleteMapping(DELETE_CAR_TO_BE_DELIVERED)
    public AckDTO deleteCareToBeDelivered(@PathVariable("car_to_be_delivered") Long id){

        return carToBeDeliveredService.deleteCarToBeDelivered(id);
    }
}
