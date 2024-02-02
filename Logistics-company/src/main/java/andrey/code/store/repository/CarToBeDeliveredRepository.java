package andrey.code.store.repository;

import andrey.code.store.entity.CarToBeDeliveredEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarToBeDeliveredRepository extends JpaRepository<CarToBeDeliveredEntity, Long> {
}
