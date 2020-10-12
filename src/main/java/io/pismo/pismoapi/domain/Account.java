package io.pismo.pismoapi.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Account")
@Table(name = "ACCOUNT")
public class Account {

    @Id
    @Column(name = "ACCOUNT_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DOCUMENT_NUMBER")
    private String documentNumber;
}
