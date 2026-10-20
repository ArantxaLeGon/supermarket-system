package com.supermarket.SaleDetail.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.supermarket.Product.entity.Product;
import com.supermarket.Product.repository.ProductRepository;
import com.supermarket.Sale.entity.Sale;
import com.supermarket.Sale.repository.SaleRepository;
import com.supermarket.SaleDetail.dto.SaleDetailRequestDTO;
import com.supermarket.SaleDetail.dto.SaleDetailResponseDTO;
import com.supermarket.SaleDetail.entity.SaleDetail;
import com.supermarket.SaleDetail.repository.SaleDetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleDetailService {
    private final SaleDetailRepository saleDetailRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    // Clase que crea el metodo de crear detalle de venta
    public SaleDetailResponseDTO createSaleDetail(SaleDetailRequestDTO request) throws Exception{

        // Validacion de la cantidad
        if(request.getAmount() == null || request.getAmount() <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La cantidad debe ser mayor a 0");
        }

        // Revision de si existe el producto
        Optional<Product> productFound = productRepository.findById(request.getIdProduct());
        if(productFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El producto no se ha encontrado");
        }

        // Revision de si existe la venta
        Optional<Sale> saleFound = saleRepository.findById(request.getIdSale());
        if(saleFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La venta no se ha encontrado");
        }

        Product product = productFound.get();

        // Registra lo ingresado
        SaleDetail saleDetail = new SaleDetail();
        saleDetail.setAmount(request.getAmount());
        saleDetail.setProduct(product);
        saleDetail.setSale(saleFound.get());

        // El precio se toma del producto y el subtotal se calcula
        saleDetail.setUnitPrice(product.getPrice());
        saleDetail.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(request.getAmount())));
        saleDetailRepository.save(saleDetail);

        // Lo transforma en response
        SaleDetailResponseDTO reponse = new SaleDetailResponseDTO();
        reponse.setId(saleDetail.getId());
        reponse.setAmount(saleDetail.getAmount());
        reponse.setUnitPrice(saleDetail.getUnitPrice());
        reponse.setSubtotal(saleDetail.getSubtotal());
        reponse.setIdProduct(saleDetail.getProduct().getId());
        reponse.setIdSale(saleDetail.getSale().getId());

        return reponse;
    };

    // Clase que llama a todos los detalles de venta
    public List<SaleDetailResponseDTO> getallSaleDetail(){

        // Crea un array para todos los detalles
        List<SaleDetailResponseDTO> reponse = new ArrayList<>();

        // Busca todos los registros
        List<SaleDetail> detailsfound = saleDetailRepository.findAll();

        // Registro por registro los añade al array
        for(SaleDetail SaleDetailfound: detailsfound){

            // Pasa la informacion al response
            SaleDetailResponseDTO saleDetail = new SaleDetailResponseDTO();
            saleDetail.setId(SaleDetailfound.getId());
            saleDetail.setAmount(SaleDetailfound.getAmount());
            saleDetail.setUnitPrice(SaleDetailfound.getUnitPrice());
            saleDetail.setSubtotal(SaleDetailfound.getSubtotal());
            saleDetail.setIdProduct(SaleDetailfound.getProduct().getId());
            saleDetail.setIdSale(SaleDetailfound.getSale().getId());

            reponse.add(saleDetail);
        }

        return reponse;
    };

    // Clase que llama a un detalle por ID
    public SaleDetailResponseDTO getIdSaleDetail(Long Id) throws Exception {
        Optional<SaleDetail> SaleDetailFound = saleDetailRepository.findById(Id);

        // Validacion si no se encuentra
        if(SaleDetailFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El detalle de venta no se ha encontrado");
        };

        // Busca el id y obtiene la info
        SaleDetail saleDetail = SaleDetailFound.get();

        SaleDetailResponseDTO reponse = new SaleDetailResponseDTO();
        reponse.setId(saleDetail.getId());
        reponse.setAmount(saleDetail.getAmount());
        reponse.setUnitPrice(saleDetail.getUnitPrice());
        reponse.setSubtotal(saleDetail.getSubtotal());
        reponse.setIdProduct(saleDetail.getProduct().getId());
        reponse.setIdSale(saleDetail.getSale().getId());

        return reponse;
    };

    // Clase que llama a los detalles de una venta
    public List<SaleDetailResponseDTO> getSaleDetailBySale(Long saleId){

        List<SaleDetailResponseDTO> reponse = new ArrayList<>();

        List<SaleDetail> detailsfound = saleDetailRepository.findBySaleId(saleId);

        for(SaleDetail SaleDetailfound: detailsfound){
            SaleDetailResponseDTO saleDetail = new SaleDetailResponseDTO();
            saleDetail.setId(SaleDetailfound.getId());
            saleDetail.setAmount(SaleDetailfound.getAmount());
            saleDetail.setUnitPrice(SaleDetailfound.getUnitPrice());
            saleDetail.setSubtotal(SaleDetailfound.getSubtotal());
            saleDetail.setIdProduct(SaleDetailfound.getProduct().getId());
            saleDetail.setIdSale(SaleDetailfound.getSale().getId());

            reponse.add(saleDetail);
        }

        return reponse;
    };

    // Clase que actualiza la informacion de un ID
    public SaleDetailResponseDTO putSaleDetail(Long Id, SaleDetailRequestDTO request) throws Exception{

        Optional<SaleDetail> SaleDetailIdFound = saleDetailRepository.findById(Id);
        if(SaleDetailIdFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Detalle de venta no encontrado o Id no existente");
        };

        if(request.getAmount() == null || request.getAmount() <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La cantidad debe ser mayor a 0");
        }

        Optional<Product> productFound = productRepository.findById(request.getIdProduct());
        if(productFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El producto no se ha encontrado");
        }

        Optional<Sale> saleFound = saleRepository.findById(request.getIdSale());
        if(saleFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La venta no se ha encontrado");
        }

        Product product = productFound.get();

        SaleDetail saleDetail = SaleDetailIdFound.get();
        saleDetail.setAmount(request.getAmount());
        saleDetail.setProduct(product);
        saleDetail.setSale(saleFound.get());
        saleDetail.setUnitPrice(product.getPrice());
        saleDetail.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(request.getAmount())));
        saleDetailRepository.save(saleDetail);

        SaleDetailResponseDTO reponse = new SaleDetailResponseDTO();
        reponse.setId(saleDetail.getId());
        reponse.setAmount(saleDetail.getAmount());
        reponse.setUnitPrice(saleDetail.getUnitPrice());
        reponse.setSubtotal(saleDetail.getSubtotal());
        reponse.setIdProduct(saleDetail.getProduct().getId());
        reponse.setIdSale(saleDetail.getSale().getId());

        return reponse;
    };

    public void deleteSaleDetail(Long Id) throws Exception{

       if (saleDetailRepository.existsById(Id)){
            saleDetailRepository.deleteById(Id);
       } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Detalle de venta no encontrado o Id inexistente");
       }
    };

}
