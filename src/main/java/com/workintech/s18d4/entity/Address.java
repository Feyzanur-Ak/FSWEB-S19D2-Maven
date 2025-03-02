package com.workintech.s18d4.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="address",schema = "public")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "street")
    private String street;
    @Column(name = "no")
    private int no;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "description")
    private String description;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    //adresle birlikte refresh, diğer işlemler yapılabilir
    private Customer customer;
}