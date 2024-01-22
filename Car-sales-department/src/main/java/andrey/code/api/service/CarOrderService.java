package andrey.code.api.service;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.CarOrderDTO;
import andrey.code.store.entity.CarEntity;
import andrey.code.store.entity.CarOrderEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CarOrderService {

    public CarOrderDTO createCarOrder(
            @PathVariable("manager_id") Long managerId,
            @ModelAttribute CarOrderEntity carOrder);

    public List<CarOrderDTO> getAllCarOrders();

    public AckDTO deleteCarOrder(@PathVariable("car_order_id") Long id);
}
