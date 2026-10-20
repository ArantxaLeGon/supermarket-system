package com.supermarket.Product.dto;


import java.math.BigDecimal;

import lombok.Data;

@Data 
public class ProductRequestDTO {
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String barcode;
    private Long idCategory;
    private Long idSupplier;

}
