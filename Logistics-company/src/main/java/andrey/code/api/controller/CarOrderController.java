package andrey.code.api.controller;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.CarOrderDTO;
import andrey.code.api.service.CarOrderService;
import andrey.code.store.entity.CarOrderEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarOrderController {

    CarOrderService carOrderService;

    private static final String GET_ALL_CAR_ORDERS = "/api/orders";
    private static final String UPDATE_CAR_ORDER = "/api/orders/{car_order_id}";
    private static final String DELETE_CAR_ORDER = "/api/orders/{car_order_id}";

    @GetMapping(GET_ALL_CAR_ORDERS)
    public List<CarOrderDTO> getAllCarOrders(){

        return carOrderService.getCarOrders();
    }

    @PatchMapping(UPDATE_CAR_ORDER)
    public CarOrderDTO updateCarOrder(
            @PathVariable("car_order_id") Long id,
            @RequestParam ("logist_id") Long logistId,
            @ModelAttribute CarOrderEntity carOrder){

        return carOrderService.updateCarOrder(id, logistId, carOrder);
    }

    @DeleteMapping(DELETE_CAR_ORDER)
    public AckDTO deleteCarOrder(
            @PathVariable("car_order_id") Long id){

        return carOrderService.deleteCarOrder(id);
    }
}
