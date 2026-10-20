package com.supermarket.Category.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.Category.dto.CategoryRequestDTO;
import com.supermarket.Category.dto.CategoryResponseDTO;
import com.supermarket.Category.service.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController 
@RequiredArgsConstructor 
@RequestMapping("api/category")
public class CategoryController {
    private final CategoryService categoryService;
    
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> crearteCategory (@RequestBody CategoryRequestDTO request) throws Exception{
        CategoryResponseDTO response = categoryService.crearteCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    };
    
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getallCategory() {
        List<CategoryResponseDTO> response = categoryService.getallCategory();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getIdCategory (@PathVariable Long id) throws Exception{
        CategoryResponseDTO response = categoryService.getIdCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> putCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO request) throws Exception {
        CategoryResponseDTO response = categoryService.putCategory(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws Exception{
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
     }

}
