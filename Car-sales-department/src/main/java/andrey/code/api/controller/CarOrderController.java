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

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class CarOrderController {

    CarOrderService carOrderService;

    private static final String CREATE_ORDER = "/api/managers/{manager_id}/orders";
    private static final String GET_ALL_ORDERS = "/api/orders";
    private static final String DELETE_ORDER = "/api/orders/{car_order_id}";


    @PostMapping(CREATE_ORDER)
    public CarOrderDTO createCarOrder(
            @PathVariable("manager_id") Long managerId,
            @ModelAttribute CarOrderEntity carOrder){

        return carOrderService.createCarOrder(managerId,carOrder);
    }


    @GetMapping(GET_ALL_ORDERS)
    public List<CarOrderDTO> getAllCarOrders(){

        return carOrderService.getAllCarOrders();
    }


    @DeleteMapping(DELETE_ORDER)
    public AckDTO deleteCarOrder(
            @PathVariable("car_order_id") Long id){

        return carOrderService.deleteCarOrder(id);
    }
}
