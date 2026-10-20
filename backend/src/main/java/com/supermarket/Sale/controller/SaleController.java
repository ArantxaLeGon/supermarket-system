package com.supermarket.Sale.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.Sale.dto.SaleResponseDTO;
import com.supermarket.Sale.dto.SalesRequestDTO;
import com.supermarket.Sale.service.SaleServices;

import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
@RequestMapping ("api/sale")
public class SaleController {
    private final SaleServices saleServices;

        @PostMapping
    public ResponseEntity<SaleResponseDTO> crearteSale (@RequestBody SalesRequestDTO request) throws Exception{
        SaleResponseDTO response = saleServices.crearteSale(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    };
    
    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> getallSale() {
        List<SaleResponseDTO> response = saleServices.getallSale();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> getIdSale (@PathVariable Long id) throws Exception{
        SaleResponseDTO response = saleServices.getIdSale(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteSale(@PathVariable Long id) throws Exception{
        saleServices.deleteSale(id);
        return ResponseEntity.noContent().build();
     }

    //  Otras busquedas

    @GetMapping("/client/{idClient}")
    public ResponseEntity<List<SaleResponseDTO>> byClient(@PathVariable Long idClient) {
        List<SaleResponseDTO> response = saleServices.getByClient(idClient);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    @GetMapping("/date")
    public ResponseEntity<List<SaleResponseDTO>> byDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        List<SaleResponseDTO>response= saleServices.getByDate(start, end);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
