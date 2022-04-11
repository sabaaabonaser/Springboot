package com.example.restapp;

import jdk.jfr.DataAmount;
import lombok.Data;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Objects;

/**
 * A Domain Object for our project.
 * Lombok library - generates: getters,setters,equals,HashCode,toString
 */
@Data
@Entity
public class Product implements Comparable<Product> {
    @Id @GeneratedValue private Long id;
    private String productName;
    private String category;
    private Double price;
    private String description;
    @ManyToOne private Order order;

    public Product(){

    }

    public Product(String name, String category,Double price,String description){
        this.productName = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.order = null;
    }

    public Product(Product other){
        this.productName = other.productName;
        this.category = other.category;
        this.price = other.price;
        this.description = other.description;
        this.order = other.order;
    }


    @Override
    public int compareTo(Product other) {
        return Double.compare(this.getPrice(),other.getPrice());
    }
}
