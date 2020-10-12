package io.pismo.pismoapi.repository;

import io.pismo.pismoapi.PismoApplicationTest;
import io.pismo.pismoapi.domain.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static io.pismo.pismoapi.TestUtil.createOperationType;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@PismoApplicationTest
public class OperationTypeRepositoryTest {

    @Autowired
    private OperationTypeRepository repository;

    @Test
    @DisplayName("Save All Operation Type")
    public void saveOperationType() {

        repository.save(createOperationType("COMPRA A VISTA"));
        repository.save(createOperationType("COMPRA PARCELADA"));
        repository.save(createOperationType("SAQUE"));
        repository.save(createOperationType("PAGAMENTO"));

        List<OperationType> operationTypes = repository.findAll();

        assertNotNull(operationTypes);
        Assertions.assertTrue(operationTypes.size() > 0);

        operationTypes.forEach(op -> {
            assertNotNull(op.getDescription());
            assertNotNull(op.getId());
        });
    }
}
