package com.workintech.s18d4.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name="customer",schema = "public")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="salary")
    private Double salary;

    @OneToOne(cascade = CascadeType.ALL) //bir customerın bir addresi var sadece
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    //Bir customerın birden fazla accountı olabilir.Many olan tarafa Join kolumn yapılır
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL) // ilişkili olanda silinecek
    //customerdan bir şey silinince accounttan da silinmeli
    private List<Account> accounts;
}