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
@Entity(name = "Account")
@Table(name = "ACCOUNT")
@SequenceGenerator(sequenceName = "SQ_ACCOUNT_ID", name = "SQ_ACCOUNT_ID")
public class Account {

    @Id
    @Column(name = "ACCOUNT_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SQ_ACCOUNT_ID")
    private Long id;

    @Column(name = "DOCUMENT_NUMBER")
    private String documentNumber;
}
