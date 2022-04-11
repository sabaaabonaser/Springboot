package com.example.restapp;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue private Long id;
    private LocalDate purchaseDate;
    private String title;
    private double price;
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "products")
    private List<Product> productsList;

    public Order(){

    }
    public Order(LocalDate purchaseDate, String title, double price, List<Product> productsList) {
        this.purchaseDate = purchaseDate;
        this.title = title;
        this.price = price;
        this.productsList = productsList;
    }

    public Order(Order other){
        this.purchaseDate = other.purchaseDate;
        this.title = other.title;
        this.price = other.price;
        this.productsList = other.productsList;
    }

}
