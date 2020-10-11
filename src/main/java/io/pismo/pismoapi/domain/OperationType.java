package io.pismo.pismoapi.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "OperationsType")
@Table(name = "OPERATIONS_TYPE")
@SequenceGenerator(sequenceName = "SQ_OPERATIONS_TYPE_ID", name = "SQ_OPERATIONS_TYPE_ID")
public class OperationType {

    @Id
    @Column(name = "OPERATIONS_TYPE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_OPERATIONS_TYPE_ID")
    private Long id;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
}
