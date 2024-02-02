package andrey.code.api.service.impl;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.CarOrderDTO;
import andrey.code.api.exceptions.BadRequestException;
import andrey.code.api.exceptions.NotFoundException;
import andrey.code.api.factory.CarOrderDTOFactory;
import andrey.code.api.service.CarOrderService;
import andrey.code.rabbitmq.RabbitMQJsonProducer;
import andrey.code.store.entity.CarOrderEntity;
import andrey.code.store.entity.CarToBeDeliveredEntity;
import andrey.code.store.entity.LogistEntity;
import andrey.code.store.repository.CarOrderRepository;
import andrey.code.store.repository.LogistRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import static andrey.code.store.enums.OrderStatus.ARRIVED;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarOrderServiceImpl implements CarOrderService {

    LogistRepository logistRepository;
    RabbitMQJsonProducer jsonProducer;
    CarOrderRepository carOrderRepository;
    CarOrderDTOFactory carOrderDTOFactory;


    @Override
    @Transactional
    public List<CarOrderDTO> getCarOrders() {

        List<CarOrderEntity> orders = carOrderRepository.findAll();

        if(orders.isEmpty()){
            throw new NotFoundException("Orders list is empty");
        }

        return orders.stream()
                .map(carOrderDTOFactory::createCarOrderDTO)
                .toList();
    }


    @Override
    @Transactional
    public CarOrderDTO updateCarOrder(
            @PathVariable("car_order_id") Long id,
            @RequestParam (value = "logist_id", required = true) Long logistId,
            @ModelAttribute CarOrderEntity carOrder) {


        CarOrderEntity carOrderEntity = carOrderRepository.findById(id).orElseThrow(()
                -> new BadRequestException("Car order with this id doesn't exist"));

            LogistEntity logist = logistRepository.findById(logistId)
                    .orElseThrow(() -> new NotFoundException("Logist with this id doesn't exist"));

            carOrderEntity.setLogist(logist);

            if (carOrder.getTitle() != null && !carOrder.getTitle().trim().isEmpty()) {
                carOrderEntity.setTitle(carOrder.getTitle());
            }
            if (carOrder.getAttachedInfo() != null && !carOrder.getAttachedInfo().trim().isEmpty()) {
                carOrderEntity.setAttachedInfo(carOrder.getAttachedInfo());
            }
            if (carOrder.getStatus() != null) {
                carOrderEntity.setStatus(carOrder.getStatus());
            }

            carOrderRepository.saveAndFlush(carOrderEntity);

            if(carOrderEntity.getStatus() == ARRIVED){
                jsonProducer.sendJsonCarOrderDTO(carOrderDTOFactory.createCarOrderDTO(carOrderEntity));
            }

            return carOrderDTOFactory.createCarOrderDTO(carOrderEntity);
    }


    @Override
    @Transactional
    public AckDTO deleteCarOrder(
            @PathVariable("car_order_id") Long id) {

        if(carOrderRepository.findById(id).isEmpty()){
            throw new NotFoundException("Car order with this id doesn't exist");
        }

        carOrderRepository.deleteById(id);

        return AckDTO.builder().answer(true).build();
    }
}
