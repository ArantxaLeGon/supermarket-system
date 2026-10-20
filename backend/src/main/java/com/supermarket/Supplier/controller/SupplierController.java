package com.supermarket.Supplier.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.Supplier.dto.SupplierRequestDTO;
import com.supermarket.Supplier.dto.SupplierResponseDTO;
import com.supermarket.Supplier.service.SupplierService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController 
@RequiredArgsConstructor 
@RequestMapping ("api/supplier")
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier (@RequestBody SupplierRequestDTO request) throws Exception{
        SupplierResponseDTO response = supplierService.createSupplier(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    };

    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> getallSupplier() {
        List<SupplierResponseDTO> response = supplierService.getallSupplier();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> getIdSupplier (@PathVariable Long id) throws Exception{
        SupplierResponseDTO response = supplierService.getIdSupplier(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> putSupplier(@PathVariable Long id, @RequestBody SupplierRequestDTO request) throws Exception {
        SupplierResponseDTO response = supplierService.putSupplier(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) throws Exception{
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
     }
}
