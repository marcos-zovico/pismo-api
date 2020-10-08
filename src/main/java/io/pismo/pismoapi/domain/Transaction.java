package io.pismo.pismoapi.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name = "Transaction")
@Table(name = "TRANSACTION")
@SequenceGenerator(sequenceName = "SQ_TRANSACTION_ID", name = "SQ_TRANSACTION_ID")
public class Transaction {

    @Id
    @Column(name = "TRANSACTION_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_TRANSACTION_ID")
    private Long id;

    @Column(name = "TRANSACTION_CODE", nullable = false)
    private UUID transactionCode;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", nullable = false, insertable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "OPERATION_TYPE_ID", nullable = false, insertable = false)
    private OperationsType operationsType;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "EVENT_DATE")
    private LocalDateTime eventDate;

}
