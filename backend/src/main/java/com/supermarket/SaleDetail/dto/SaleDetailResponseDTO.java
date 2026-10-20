package com.supermarket.SaleDetail.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SaleDetailResponseDTO {
    private Long id;

    private Integer amount;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;

    private Long idProduct;

    private Long idSale;

}
