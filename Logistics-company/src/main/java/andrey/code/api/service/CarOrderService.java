package andrey.code.api.service;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.CarOrderDTO;
import andrey.code.store.entity.CarOrderEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CarOrderService {

    public List<CarOrderDTO> getCarOrders();

    public CarOrderDTO updateCarOrder(
            @PathVariable("car_order_id") Long id,
            @ModelAttribute CarOrderEntity carOrder);

    public AckDTO deleteCarOrder(
            @PathVariable("car_order_id") Long id);

}
