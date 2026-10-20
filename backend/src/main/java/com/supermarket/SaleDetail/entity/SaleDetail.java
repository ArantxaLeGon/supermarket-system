package com.supermarket.SaleDetail.entity;

import java.math.BigDecimal;

import com.supermarket.Product.entity.Product;
import com.supermarket.Sale.entity.Sale;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="sale_detail")
public class SaleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id")
    private Long id;

    @Column (name="amount")
    private Integer amount;

    @Column (name="unit_price")
    private BigDecimal unitPrice;

    @Column (name="subtotal")
    private BigDecimal subtotal;

    // Muchos detalles pertenecen a un producto
    @ManyToOne
    @JoinColumn (name="id_product")
    private Product product;

    // Muchos detalles pertenecen a una venta
    @ManyToOne
    @JoinColumn (name="id_sale")
    private Sale sale;

}
