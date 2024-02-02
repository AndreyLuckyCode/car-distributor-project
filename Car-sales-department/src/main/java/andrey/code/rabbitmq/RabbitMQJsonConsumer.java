package andrey.code.rabbitmq;

import andrey.code.api.dto.CarOrderDTO;
import andrey.code.store.entity.CarOrderEntity;
import andrey.code.store.repository.CarOrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static andrey.code.store.entity.enums.OrderStatus.ARRIVED;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RabbitMQJsonConsumer {

    CarOrderRepository carOrderRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    @RabbitListener(queues = "car_order_queue2")
    public void consumeJsonMessage(CarOrderDTO carOrderDTO) {
        LOGGER.info(String.format("Received Json message -> %s", carOrderDTO.toString()));

        CarOrderEntity carOrder = new CarOrderEntity();

        carOrder.setId(carOrderDTO.getId());
        carOrder.setTitle(carOrderDTO.getTitle());
        carOrder.setAttachedInfo(carOrderDTO.getAttachedInfo());
        carOrder.setStatus(ARRIVED);

        carOrderRepository.saveAndFlush(carOrder);

        LOGGER.info("New car order was successfully saved in data base");
    }
}
