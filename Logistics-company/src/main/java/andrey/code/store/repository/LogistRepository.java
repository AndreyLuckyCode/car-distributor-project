package andrey.code.store.repository;

import andrey.code.store.entity.LogistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogistRepository extends JpaRepository<LogistEntity, Long> {
}
