package com.supermarket.SaleDetail.dto;

import lombok.Data;

@Data
public class SaleDetailRequestDTO {

    private Integer amount;

    private Long idProduct;

    private Long idSale;

}
