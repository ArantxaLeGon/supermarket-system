package com.supermarket.Sale.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermarket.Sale.entity.Sale;
import com.supermarket.SaleDetail.entity.SaleDetail;

import java.time.LocalDateTime;
import java.util.List;


public interface SaleRepository extends JpaRepository<Sale,Long>{
    // Lista de los detalles de venta
    List<Sale> findBySaleDetails(List<SaleDetail> saleDetails);

    // Lista de ventas realizadas por 1 cliente
    List<Sale> findByIdClientId(Long idClient);
    
    // Lista de ventas en un intervalo de tiempo
    List<Sale> findBySaleDateBetween(LocalDateTime start, LocalDateTime end);
}
