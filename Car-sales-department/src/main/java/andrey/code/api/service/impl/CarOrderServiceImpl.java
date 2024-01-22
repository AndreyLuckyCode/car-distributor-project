package andrey.code.api.service.impl;

import andrey.code.api.dto.AckDTO;
import andrey.code.api.dto.CarOrderDTO;
import andrey.code.api.exceptions.BadRequestException;
import andrey.code.api.exceptions.NotFoundException;
import andrey.code.api.factory.CarOrderDTOFactory;
import andrey.code.api.service.CarOrderService;
import andrey.code.store.entity.CarOrderEntity;
import andrey.code.store.entity.ManagerEntity;
import andrey.code.store.repository.CarOrderRepository;
import andrey.code.store.repository.ManagerRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CarOrderServiceImpl implements CarOrderService {

    ManagerRepository managerRepository;
    CarOrderRepository carOrderRepository;
    CarOrderDTOFactory carOrderDTOFactory;

    @Override
    @Transactional
    public CarOrderDTO createCarOrder(
            @PathVariable("manager_id") Long managerId,
            @ModelAttribute CarOrderEntity carOrder) {

        ManagerEntity managerEntity = managerRepository.findById(managerId).orElseThrow(()
                -> new NotFoundException("Manager with that id doesn't exist."));
        carOrder.setManager(managerEntity);

        if(carOrder.getTitle() == null || carOrder.getTitle().trim().isEmpty()){
            throw new BadRequestException("Title can't be empty");
        }
        if(carOrder.getAttachedInfo() == null || carOrder.getAttachedInfo().trim().isEmpty()){
            throw new BadRequestException("Please, add attached info");
        }

        carOrderRepository.saveAndFlush(carOrder);

        return carOrderDTOFactory.createCarOrderDTO(carOrder);
    }


    @Override
    @Transactional
    public List<CarOrderDTO> getAllCarOrders() {


        List<CarOrderEntity> carOrders = carOrderRepository.findAll();

        if(carOrders.isEmpty()){
            throw new NotFoundException("Car order list is empty");
        }

        return carOrders.stream()
                .map(carOrderDTOFactory::createCarOrderDTO)
                .toList();
    }


    @Override
    @Transactional
    public AckDTO deleteCarOrder(@PathVariable("car_order_id") Long id) {

        if(carOrderRepository.findById(id).isEmpty()){
            throw new NotFoundException("Car order with that ID doesn't exist");
        }

        carOrderRepository.deleteById(id);

        return AckDTO.builder().answer(true).build();
    }
}
