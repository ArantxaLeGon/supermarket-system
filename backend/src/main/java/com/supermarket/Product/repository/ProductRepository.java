package com.supermarket.Product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermarket.Product.entity.Product;


public interface ProductRepository extends JpaRepository<Product,Long>{

    // Busca producto por codigo de barras
    Optional<Product>findByBarcode(String barcode);

    // Busca si existe un producto en una categoria
    boolean existsByIdCategoryId (Long id_category);

    // Busca si existe un producto en un proveedor
    boolean existsByIdSupplierId (Long id_supplier);

    // Crea una lista de productos que coincidan con las palabras
    List<Product> findByNameContainingIgnoreCase(String name);

    // Crea una lista de productos que tengan stock bajo
    List<Product> findByStockLessThanEqual(Integer limit);


}