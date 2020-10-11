package io.pismo.pismoapi.repository;

import io.pismo.pismoapi.domain.Account;
import io.pismo.pismoapi.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t where t.account.id = :#{#account.id}")
    Page<Transaction> listByAccount(@Param("account") Account account, final Pageable pageable);

    Optional<Transaction> findByTransactionCode(final String transactionCode);
}
