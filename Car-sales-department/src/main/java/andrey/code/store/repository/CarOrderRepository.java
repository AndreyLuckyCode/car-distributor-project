package andrey.code.store.repository;

import andrey.code.store.entity.CarOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarOrderRepository extends JpaRepository<CarOrderEntity, Long> {
}
