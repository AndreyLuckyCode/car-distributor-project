package andrey.code.api.service;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.CarDTO;
import andrey.code.store.entity.CarEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CarService {

    public CarDTO createCar(
            @ModelAttribute CarEntity car);

    public List<CarDTO> getAllCarsByManagerId(
            @PathVariable("manager_id") Long id);

    public List<CarDTO> getNonSoldCars();

    public List<CarDTO> getSoldCars();

    public CarDTO updateOrSellCar(
            @PathVariable("car_id") Long id,
            @RequestParam(value = "manager_id", required = false) Long managerId,
            @ModelAttribute CarEntity car);

    public AckDTO deleteCar(
            @PathVariable("car_id") Long id);
}
