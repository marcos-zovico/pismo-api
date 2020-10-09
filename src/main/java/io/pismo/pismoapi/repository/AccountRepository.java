package io.pismo.pismoapi.repository;

import java.util.Optional;

import io.pismo.pismoapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByDocumentNumber(final String documentNumber);
}
