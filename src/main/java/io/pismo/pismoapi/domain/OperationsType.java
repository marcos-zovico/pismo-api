package io.pismo.pismoapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name = "OperationsType")
@Table(name = "OPERATIONS_TYPE")
@SequenceGenerator(sequenceName = "SQ_OPERATIONS_TYPE_ID", name = "SQ_OPERATIONS_TYPE_ID")
public class OperationsType {

    @Id
    @Column(name = "OPERATIONS_TYPE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_OPERATIONS_TYPE_ID")
    private Long id;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

}
