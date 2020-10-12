package io.pismo.pismoapi.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "OperationType")
@Table(name = "OPERATION_TYPE")
public class OperationType {

    @Id
    @Column(name = "OPERATION_TYPE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
}
