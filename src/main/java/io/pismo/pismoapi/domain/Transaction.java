package io.pismo.pismoapi.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "Transaction")
@Table(name = "TRANSACTION")
@SequenceGenerator(sequenceName = "SQ_TRANSACTION_ID", name = "SQ_TRANSACTION_ID")
public class Transaction {

    @Id
    @Column(name = "TRANSACTION_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_TRANSACTION_ID")
    private Long id;

    @Column(name = "TRANSACTION_CODE", nullable = false, unique = true)
    private String transactionCode;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "OPERATION_TYPE_ID")
    private OperationType operationType;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "EVENT_DATE", nullable = false)
    private LocalDateTime eventDate;

}
