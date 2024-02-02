package andrey.code.api.service;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.CarToBeDeliveredDTO;
import andrey.code.store.entity.CarToBeDeliveredEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CarToBeDeliveredService {

    public CarToBeDeliveredDTO createCarToBeDelivered(
            @ModelAttribute CarToBeDeliveredEntity carToBeDelivered);

    public List<CarToBeDeliveredDTO> getAllCarsToBeDelivered();

    public CarToBeDeliveredDTO updateCarToBeDelivered(
            @PathVariable("car_to_be_delivered_id") Long id,
            @ModelAttribute CarToBeDeliveredEntity carToBeDelivered);

    public AckDTO deleteCarToBeDelivered(
            @PathVariable("car_to_be_delivered") Long id);
}
