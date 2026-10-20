package com.supermarket.Client.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supermarket.Sale.entity.Sale;

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
@Table (name="client")
public class Client {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="document")
    private String document;

    @Column(name="phone")
    private String phone;

    @OneToMany(mappedBy = "idClient")
    @JsonIgnore 
    private List<Sale>sales = new ArrayList<>(); 
}
