package io.pismo.pismoapi.repository;

import io.pismo.pismoapi.domain.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationsTypeRepository extends JpaRepository<OperationType, Long> {
}
