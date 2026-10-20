package com.supermarket.Sale.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supermarket.Client.entity.Client;
import com.supermarket.SaleDetail.entity.SaleDetail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Data
@Table (name="sale")
public class Sale {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;

    @Column (name="sale_date")
    private LocalDateTime saleDate;
    
    @Column (name="total")
    private BigDecimal total;

    @ManyToOne 
    @JoinColumn (name="id_client")
    private Client idClient;

    @OneToMany (mappedBy = "sale")
    @JsonIgnore 
    private List<SaleDetail> saleDetails = new ArrayList<>();
}
