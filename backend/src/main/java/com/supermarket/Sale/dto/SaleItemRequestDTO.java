package com.supermarket.Sale.dto;

import lombok.Data;

@Data 
public class SaleItemRequestDTO {
    private Long idProduct;
    private Integer amount;
}
