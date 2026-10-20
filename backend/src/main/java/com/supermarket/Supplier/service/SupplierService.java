package com.supermarket.Supplier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.supermarket.Product.repository.ProductRepository;
import com.supermarket.Supplier.dto.SupplierRequestDTO;
import com.supermarket.Supplier.dto.SupplierResponseDTO;
import com.supermarket.Supplier.entity.Supplier;
import com.supermarket.Supplier.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    // Clase que crea el metodo de crear proveedor
    public SupplierResponseDTO createSupplier(SupplierRequestDTO request) throws Exception{
        // Revision de si exite el nombre
        Optional<Supplier> Supplierfound = supplierRepository.findFirstByEmail(request.getEmail());
        
        // Si existe, parece error
        if(Supplierfound.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"El correo ya esta en uso");
        }

        if(request.getName() == null || request.getName().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligtorio");
        }

        // Reistra lo ingresado
        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplierRepository.save(supplier);

        // Lo trasforma en response
        SupplierResponseDTO reponse = new SupplierResponseDTO();
        reponse.setId(supplier.getId());
        reponse.setName(supplier.getName());
        reponse.setPhone(supplier.getPhone());
        reponse.setEmail(supplier.getEmail());

        return reponse;
    };

    // Clase que llama a todos las proveedor
    public List<SupplierResponseDTO> getallSupplier(){
        
        // Crea un array para todos las proveedor
        List<SupplierResponseDTO> reponse = new ArrayList<>();

        // Busca todos los registros
        List<Supplier> suppliersfound = supplierRepository.findAll();
        
        // Registro por registro los añade al array
        for(Supplier Supplierfound: suppliersfound){

            // Pasa la informacion al response
            SupplierResponseDTO supplier = new SupplierResponseDTO();
            supplier.setId(Supplierfound.getId());
            supplier.setName(Supplierfound.getName());
            supplier.setPhone(Supplierfound.getPhone());
            supplier.setEmail(Supplierfound.getEmail());

            reponse.add(supplier);
        }

        return reponse;
    };

    // Clase que llama a una proveedor por ID
    public SupplierResponseDTO getIdSupplier(Long Id) throws Exception {
        Optional<Supplier> SupplierFound = supplierRepository.findById(Id);

        // Validacion si se encuentra error
        if(SupplierFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"El provedor no se ha encontrado");
        };

        // Busca el id y obtene la info
        Supplier supplier = SupplierFound.get();

        SupplierResponseDTO reponse = new SupplierResponseDTO();
        reponse.setId(supplier.getId());
        reponse.setName(supplier.getName());
        reponse.setPhone(supplier.getPhone());
        reponse.setEmail(supplier.getEmail());

        return reponse;
    };

    // Clase que actualiza la informacion de un ID
    public SupplierResponseDTO putSupplier(Long Id, SupplierRequestDTO request) throws Exception{
        
        Optional<Supplier> SupplierIdFound = supplierRepository.findById(Id);
        if(SupplierIdFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"El provedor no se ha encontrado");
        };
        
        Optional<Supplier> SupplierNameFound = supplierRepository.findFirstByEmail(request.getEmail());
        if(SupplierNameFound.isPresent()){
            throw new Exception("Proveedor ya existente");
        };

        if(request.getName() == null || request.getName().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligtorio");
        }

        Supplier supplier = SupplierIdFound.get();
        supplier.setName(request.getName());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplierRepository.save(supplier);


        SupplierResponseDTO reponse = new SupplierResponseDTO();
        reponse.setId(supplier.getId());
        reponse.setName(supplier.getName());
        reponse.setPhone(supplier.getPhone());
        reponse.setEmail(supplier.getEmail());

        return reponse;
    };

    public void deleteSupplier(Long Id) throws Exception{

       if (supplierRepository.existsById(Id)){
            if(productRepository.existsByIdSupplierId(Id)){
                throw new ResponseStatusException(HttpStatus.CONFLICT ,"El proveedor tiene productos");
            }else{
                supplierRepository.deleteById(Id);
            }
       } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"El provedor no se ha encontrado");
       }
    };

}



