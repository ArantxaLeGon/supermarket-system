package com.supermarket.Sale.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.supermarket.SaleDetail.dto.SaleDetailResponseDTO;

import lombok.Data;

@Data
public class SaleResponseDTO {
    private Long id;
    private LocalDateTime saleDate;
    private BigDecimal total;
    private Long idClient;
    private List<SaleDetailResponseDTO> details;
}
