package com.intuit.accountprocessor.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Account")
@Data
@AllArgsConstructor(staticName = "of")
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="account_id")
    private long accountId;
    @Column(name="first_name", nullable=false, length=50)
    private String firstName;

    @Column(name="last_name", nullable=false, length=50)
    private String lastName;

    @Column(name="email_address", nullable=false, length=50,unique = true)
    private String emailAddress;

    @ManyToOne(optional=false,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private StdAddress stdAddress;

    public Account(){

    }
}
