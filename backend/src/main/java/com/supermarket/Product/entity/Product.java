package com.supermarket.Product.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supermarket.Category.entity.Category;
import com.supermarket.SaleDetail.entity.SaleDetail;
import com.supermarket.Supplier.entity.Supplier;

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

@Data 
@Entity 
@Table (name="product")
public class Product {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;

    @Column (name="name")
    private String name;

    @Column (name="price")
    private BigDecimal price;

    @Column (name="stock")
    private Integer stock;

    @Column (name="barcode")
    private String barcode;

    @ManyToOne
    @JoinColumn (name="id_category")
    private Category idCategory;

    @ManyToOne
    @JoinColumn (name="id_supplier")
    private Supplier idSupplier;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<SaleDetail> saleDetails = new ArrayList<>();
}
