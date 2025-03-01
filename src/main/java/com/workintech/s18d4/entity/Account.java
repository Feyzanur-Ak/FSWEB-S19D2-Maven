package com.workintech.s18d4.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="acconut", schema="public")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private long id;

    @Column(name ="account_name")
    private String accountName;
    @Column(name ="money_amount")
    private Double moneyAmount;

    @JsonBackReference // sürekli döngü olmaması için
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    //account silinse bile customer silinmeyecek
    @JoinColumn(name = "customer_id", referencedColumnName = "id") //many kısımda join yapıldı
    //customer ile customer_id üzerinden bağlanacak
    private Customer customer;
}
