package com.supermarket.Sale.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.Sale.dto.SaleResponseDTO;
import com.supermarket.Sale.dto.SalesRequestDTO;
import com.supermarket.Sale.service.SaleServices;

import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
@RequestMapping ("/Sale")
public class SaleController {
    private final SaleServices saleServices;

        @PostMapping("/Post")
    public ResponseEntity<SaleResponseDTO> crearteSale (@RequestBody SalesRequestDTO request) throws Exception{
        SaleResponseDTO response = saleServices.crearteSale(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    };
    
    @GetMapping("/Get")
    public ResponseEntity<List<SaleResponseDTO>> getallSale() {
        List<SaleResponseDTO> response = saleServices.getallSale();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/Get/{id}")
    public ResponseEntity<SaleResponseDTO> getIdSale (@PathVariable Long id) throws Exception{
        SaleResponseDTO response = saleServices.getIdSale(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
     @DeleteMapping("/Delete/{id}")
     public ResponseEntity<Void> deleteSale(@PathVariable Long id) throws Exception{
        saleServices.deleteSale(id);
        return ResponseEntity.noContent().build();
     }
}
