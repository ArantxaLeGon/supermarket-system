package com.supermarket.Category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.supermarket.Category.dto.CategoryRequestDTO;
import com.supermarket.Category.dto.CategoryResponseDTO;
import com.supermarket.Category.entity.Category;
import com.supermarket.Category.repository.CategoryRepository;
import com.supermarket.Product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CategoryService {
    // Se llama al repository
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    // Clase que crea el metodo de crear categorias
    public CategoryResponseDTO crearteCategory(CategoryRequestDTO request) throws Exception{
        // Revision de si exite el nombre
        Optional<Category> categoryfound = categoryRepository.findFirstByName(request.getName());
        
        // Si existe, parece error
        if(categoryfound.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Esta categoria ya esta en uso");
        }

        if(request.getName() == null || request.getName().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligtorio");
        }

        // Reistra lo ingresado
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);

        // Lo trasforma en response
        CategoryResponseDTO reponse = new CategoryResponseDTO();
        reponse.setId(category.getId());
        reponse.setName(category.getName());
        reponse.setDescription(category.getDescription());

        return reponse;
    };

    // Clase que llama a todos las categorias
    public List<CategoryResponseDTO> getallCategory(){
        
        // Crea un array para todos las categorias
        List<CategoryResponseDTO> reponse = new ArrayList<>();

        // Busca todos los registros
        List<Category> categoriesfound = categoryRepository.findAll();
        
        // Registro por registro los añade al array
        for(Category categoryfound: categoriesfound){

            // Pasa la informacion al response
            CategoryResponseDTO category = new CategoryResponseDTO();
            category.setId(categoryfound.getId());
            category.setName(categoryfound.getDescription());
            category.setDescription(categoryfound.getDescription());

            reponse.add(category);
        }

        return reponse;
    };

    // Clase que llama a una categoria por ID
    public CategoryResponseDTO getIdCategory(Long Id) throws Exception {
        Optional<Category> categoryFound = categoryRepository.findById(Id);

        // Validacion si se encuentra error
        if(categoryFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Categoria no encontrada o Id no existente");
        };

        // Busca el id y obtene la info
        Category category = categoryFound.get();

        CategoryResponseDTO reponse = new CategoryResponseDTO();
        reponse.setId(category.getId());
        reponse.setName(category.getName());
        reponse.setDescription(category.getDescription());

        return reponse;
    };

    // Clase que actualiza la informacion de un ID
    public CategoryResponseDTO putCategory(Long Id, CategoryRequestDTO request) throws Exception{
        
        Optional<Category> categoryIdFound = categoryRepository.findById(Id);
        if(categoryIdFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada o Id no existente");
        };
        
        Optional<Category> categoryNameFound = categoryRepository.findFirstByName(request.getName());
        if(categoryNameFound.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria ya existente");
        };

        if(request.getName() == null || request.getName().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligtorio");
        }

        Category category = categoryIdFound.get();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);

        CategoryResponseDTO response = new CategoryResponseDTO();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());

        return response;
    };

    // Clase que elimina la categoria
    public void deleteCategory(Long Id) throws Exception{
       if (categoryRepository.existsById(Id)){
            if(productRepository.existsByIdCategoryId(Id)){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "La categoria tiene productod");
            }else{
                categoryRepository.deleteById(Id);   
            }
       } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Categoria no encontrada o Id inexistente"); 
       }
    };
};
