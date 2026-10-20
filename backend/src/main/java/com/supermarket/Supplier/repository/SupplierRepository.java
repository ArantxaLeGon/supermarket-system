package com.supermarket.Supplier.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermarket.Supplier.entity.Supplier;
// import java.util.List;


public interface SupplierRepository extends JpaRepository<Supplier,Long>{
    Optional<Supplier> findFirstByEmail(String findFirstByEmail);
}
