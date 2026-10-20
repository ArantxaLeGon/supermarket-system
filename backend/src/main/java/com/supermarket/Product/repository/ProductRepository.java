package com.supermarket.Product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermarket.Product.entity.Product;


public interface ProductRepository extends JpaRepository<Product,Long>{
    Optional<Product>findByBarcode(String barcode);

    boolean existsByIdCategoryId (Long id_category);

    boolean existsByIdSupplierId (Long id_supplier);

}