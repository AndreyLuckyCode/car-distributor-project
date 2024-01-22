package andrey.code.store.repository;

import andrey.code.store.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<CarEntity, Long> {

    public List<CarEntity> findAllByManagerId(Long id);
    public List<CarEntity> findAllByIsSoldFalse();
    public List<CarEntity> findAllByIsSoldTrue();
}
