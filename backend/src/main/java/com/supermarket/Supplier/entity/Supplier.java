package com.supermarket.Supplier.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supermarket.Product.entity.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Data 
@Table(name="supplier")
public class Supplier {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;

    @Column (name="name")
    private String name;

    @Column (name="phone")
    private String phone;

    @Column (name="email")
    private String email;

    @OneToMany (mappedBy = "idSupplier")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

}
