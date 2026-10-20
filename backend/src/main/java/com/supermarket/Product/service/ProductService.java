package com.supermarket.Product.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.supermarket.Category.entity.Category;
import com.supermarket.Category.repository.CategoryRepository;
import com.supermarket.Product.dto.ProductRequestDTO;
import com.supermarket.Product.dto.ProductResponseDTO;
import com.supermarket.Product.entity.Product;
import com.supermarket.Product.repository.ProductRepository;
import com.supermarket.Supplier.entity.Supplier;
import com.supermarket.Supplier.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service 
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

       // Clase que crea el metodo de crear productos
    public ProductResponseDTO crearteProduct(ProductRequestDTO request) throws Exception{
        // Revision de si exite el nombre
        Optional<Product> productfound = productRepository.findByBarcode(request.getBarcode());
        
        // Si existe, parece error
        if(productfound.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Codigo de barras ya esta en uso");
        }

        if(request.getName() == null || request.getName().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligtorio");
        }

        if(request.getStock() == null || request.getStock() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El stock debe ser mayor o igual a 0");
        }

        if(request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO)<0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio debe ser mayor 0");
        }

        Optional<Category> categoryFound = categoryRepository.findById(request.getIdCategory());
        if (categoryFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoria no se ha encontrado");
        }
        Optional<Supplier> supplierFound = supplierRepository.findById(request.getIdSupplier());
        if (supplierFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El proveedor no se ha encontrado");
        }

        // Reistra lo ingresado
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setBarcode(request.getBarcode());
        product.setIdCategory(categoryFound.get());
        product.setIdSupplier(supplierFound.get());
        productRepository.save(product);

        // Lo trasforma en response
        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setBarcode(product.getBarcode());
        response.setIdCategory(product.getIdCategory().getId());
        response.setIdSupplier(product.getIdSupplier().getId());

        return response;
    }

    // Clase que llama a todos las productos
    public List<ProductResponseDTO> getallProduct(){
        
        // Crea un array para todos las productos
        List<ProductResponseDTO> response = new ArrayList<>();

        // Busca todos los registros
        List<Product> productsfound = productRepository.findAll();
        
        // Registro por registro los añade al array
        for(Product productfound: productsfound){

            // Pasa la informacion al response
            ProductResponseDTO product = new ProductResponseDTO();
            product.setId(productfound.getId());
            product.setName(productfound.getName());
            product.setPrice(productfound.getPrice());
            product.setStock(productfound.getStock());
            product.setBarcode(productfound.getBarcode());
            product.setIdCategory(productfound.getIdCategory().getId());
            product.setIdSupplier(productfound.getIdSupplier().getId());

            response.add(product);
        }

        return response;
    }

    // Clase que llama a una categoria por ID
    public ProductResponseDTO getIdProduct(Long Id) throws Exception {
        Optional<Product> productFound = productRepository.findById(Id);

        // Validacion si se encuentra error
        if(productFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"Producto no encontrado o Id no existente");
        }

        // Busca el id y obtene la info
        Product product = productFound.get();

        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setBarcode(product.getBarcode());
        response.setIdCategory(product.getIdCategory().getId());
        response.setIdSupplier(product.getIdSupplier().getId());

        return response;
    }

    // Clase que actualiza la informacion de un ID
    public ProductResponseDTO putProduct(Long Id, ProductRequestDTO request) throws Exception{
        
        Optional<Product> productIdFound = productRepository.findById(Id);
        if(productIdFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"Producto no encontrado o Id no existente");
        }
        
        Optional<Product> barcodeFound = productRepository.findByBarcode(request.getBarcode());
        if (barcodeFound.isPresent() && !barcodeFound.get().getId().equals(Id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Codigo de barras ya esta en uso");
        }

        if(request.getName() == null || request.getName().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligtorio");
        }

        if(request.getStock() == null || request.getStock() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El stock debe ser mayor o igual a 0");
        }

        if(request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO)<0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio debe ser mayor 0");
        }

                Optional<Category> categoryFound = categoryRepository.findById(request.getIdCategory());
        if (categoryFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La categoria no se ha encontrado");
        }
        Optional<Supplier> supplierFound = supplierRepository.findById(request.getIdSupplier());
        if (supplierFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El proveedor no se ha encontrado");
        }

        Product product = productIdFound.get();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setBarcode(request.getBarcode());
        product.setIdCategory(categoryFound.get());
        product.setIdSupplier(supplierFound.get());
        productRepository.save(product);

        // Lo trasforma en response
        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setBarcode(product.getBarcode());
        response.setIdCategory(product.getIdCategory().getId());
        response.setIdSupplier(product.getIdSupplier().getId());

        return response;
    }

    // Clase que elimina la categoria
    public void deleteProduct(Long Id) throws Exception{

       if (productRepository.existsById(Id)){
            productRepository.deleteById(Id);
       } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Producto no encontrado o Id inexistente"); 
       }
    }
}
