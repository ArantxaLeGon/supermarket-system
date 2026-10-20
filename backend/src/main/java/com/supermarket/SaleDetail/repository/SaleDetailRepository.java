package com.supermarket.SaleDetail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermarket.SaleDetail.entity.SaleDetail;

public interface SaleDetailRepository extends JpaRepository<SaleDetail,Long>{
    // Busca todos los detalles de una venta
    List<SaleDetail> findBySaleId(Long saleId);
}
