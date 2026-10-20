package com.supermarket.Client.dto;

import lombok.Data;

@Data 
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String document;
    private String phone;
}
