package andrey.code.rabbitmq;

import andrey.code.api.dto.CarOrderDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RabbitMQJsonProducer {

//    @Value("${rabbitmq.exchange.name}")
    String exchange = "car_order_exchange2";
//    @Value("${rabbitmq.routing.json.key}")
    String jsonRoutingKey = "car_order_routing_key2";

    static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    final RabbitTemplate rabbitTemplate;


    public void sendJsonCarOrderDTO(CarOrderDTO carOrderDTO){
        LOGGER.info(String.format("Json message sent -> %s", carOrderDTO.toString()));
        rabbitTemplate.convertAndSend(exchange, jsonRoutingKey, carOrderDTO);
    }
}
