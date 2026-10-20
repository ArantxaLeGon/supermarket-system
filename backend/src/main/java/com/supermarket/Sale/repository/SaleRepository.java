package com.supermarket.Sale.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermarket.Sale.entity.Sale;
import com.supermarket.SaleDetail.entity.SaleDetail;
import java.util.List;


public interface SaleRepository extends JpaRepository<Sale,Long>{
    
    List<Sale> findBySaleDetails(List<SaleDetail> saleDetails);
}
