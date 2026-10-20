package com.supermarket.Sale.dto;

import java.util.List;

import lombok.Data;

@Data 
public class SalesRequestDTO {
        
    private Long idClient;
    private List<SaleItemRequestDTO> details;

}
