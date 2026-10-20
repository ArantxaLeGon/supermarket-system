package com.supermarket.Product.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.Product.dto.ProductRequestDTO;
import com.supermarket.Product.dto.ProductResponseDTO;
import com.supermarket.Product.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@RequiredArgsConstructor 
@RequestMapping ("api/product")
public class ProductController {
    private final ProductService productService;

    
    @PostMapping("/Post")
    public ResponseEntity<ProductResponseDTO> crearteProduct (@RequestBody ProductRequestDTO request) throws Exception{
        ProductResponseDTO response = productService.crearteProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    };
    
    @GetMapping("/Get")
    public ResponseEntity<List<ProductResponseDTO>> getallProduct() {
        List<ProductResponseDTO> response = productService.getallProduct();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/Get/{id}")
    public ResponseEntity<ProductResponseDTO> getIdProduct (@PathVariable Long id) throws Exception{
        ProductResponseDTO response = productService.getIdProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/Put/{id}")
    public ResponseEntity<ProductResponseDTO> putProduct(@PathVariable Long id, @RequestBody ProductRequestDTO request) throws Exception {
        ProductResponseDTO response = productService.putProduct(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
     @DeleteMapping("/Delete/{id}")
     public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws Exception{
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
     }

    //  Busquedas aparte
    @GetMapping("/categoria/{idCategory}")
    public ResponseEntity<List<ProductResponseDTO>> searchByCategory(@PathVariable Long idCategory) {
        List<ProductResponseDTO> response = productService.getByCategory(idCategory);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> search(@RequestParam String nombre) {
        List<ProductResponseDTO> response = productService.search(nombre);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductResponseDTO>> lowStock(@RequestParam(defaultValue = "10") Integer limit) {
        List<ProductResponseDTO> response = productService.lowStock(limit);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }    
}
