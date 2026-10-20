package com.supermarket.Sale.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.supermarket.Sale.dto.SalesRequestDTO;
import com.supermarket.Client.entity.Client;
import com.supermarket.Client.repository.ClientRepository;
import com.supermarket.Product.entity.Product;
import com.supermarket.Product.repository.ProductRepository;
import com.supermarket.Sale.dto.SaleItemRequestDTO;
import com.supermarket.Sale.dto.SaleResponseDTO;
import com.supermarket.Sale.entity.Sale;
import com.supermarket.Sale.repository.SaleRepository;
import com.supermarket.SaleDetail.dto.SaleDetailResponseDTO;
import com.supermarket.SaleDetail.entity.SaleDetail;
import com.supermarket.SaleDetail.repository.SaleDetailRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class SaleServices {
    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final SaleDetailRepository saleDetailRepository;    

    
    // Clase que crea el metodo de crear ventas
    @Transactional 
    public SaleResponseDTO crearteSale(SalesRequestDTO request) throws Exception{

        // Busca el cliente por su id y revisa si existe
        Optional<Client> clientFound = clientRepository.findById(request.getIdClient());
        if (clientFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El cliente no se ha encontrado");
        }

        // revisa si esta vacio.
        if(request.getDetails() == null || request.getDetails().isEmpty()){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La ventad debe tener productos");
        }

        Sale sale = new Sale();
        sale.setIdClient(clientFound.get());
        sale.setSaleDate(LocalDateTime.now());
        sale.setTotal(BigDecimal.ZERO);
        saleRepository.save(sale);

            BigDecimal total = BigDecimal.ZERO;

        // Producto por producto
        for (SaleItemRequestDTO item : request.getDetails()) {

            // revisa la cantidad que pidieron
            if (item.getAmount() == null || item.getAmount() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad debe ser mayor a 0");
            }

            // revisa que el producto exista
            Optional<Product> productFound = productRepository.findById(item.getIdProduct());
            if (productFound.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no se ha encontrado");
            }
            Product product = productFound.get();

            // revisa si el stock es suficiente
            if (product.getStock() < item.getAmount()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Stock insuficiente: " + product.getName());
            }

            // Descuenta el stock
            product.setStock(product.getStock() - item.getAmount());
            productRepository.save(product);

            // Crea el detalle
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getAmount()));

            SaleDetail detail = new SaleDetail();
            detail.setSale(sale);
            detail.setProduct(product);
            detail.setAmount(item.getAmount());
            detail.setUnitPrice(product.getPrice());
            detail.setSubtotal(subtotal);
            saleDetailRepository.save(detail);

            // Suma al total
            total = total.add(subtotal);
        }

        // Actualiza el total de la venta
        sale.setTotal(total);
        saleRepository.save(sale);

        // Lo transforma en response
        SaleResponseDTO response = new SaleResponseDTO();
        response.setId(sale.getId());
        response.setSaleDate(sale.getSaleDate());
        response.setTotal(sale.getTotal());
        response.setIdClient(sale.getIdClient().getId());

        return response;
    }
    
    // Clase que llama a todos las ventas
    public List<SaleResponseDTO> getallSale(){
        List<SaleResponseDTO> response = new ArrayList<>();
        List<Sale> salesFound = saleRepository.findAll();

        for(Sale saleFound : salesFound){
            
            List<SaleDetailResponseDTO> details = new ArrayList<>();

            for (SaleDetail d : saleDetailRepository.findBySaleId(saleFound.getId())) {
                SaleDetailResponseDTO detail = new SaleDetailResponseDTO();
                detail.setId(d.getId());
                detail.setAmount(d.getAmount());
                detail.setUnitPrice(d.getUnitPrice());
                detail.setSubtotal(d.getSubtotal());
                detail.setIdProduct(d.getProduct().getId());
                detail.setIdSale(d.getSale().getId());
                details.add(detail);
            }

            SaleResponseDTO sale = new SaleResponseDTO();
            sale.setId(saleFound.getId());
            sale.setSaleDate(saleFound.getSaleDate());
            sale.setTotal(saleFound.getTotal());
            sale.setIdClient(saleFound.getIdClient().getId());
            sale.setDetails(details);

            response.add(sale);
        }
        
        return response;
    }

    // Clase que llama a una venta por ID
    public SaleResponseDTO getIdSale(Long Id) throws Exception {
         Optional<Sale> SaleFound = saleRepository.findById(Id);
         
         // Validacion si no se encuentra
         if(SaleFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La venta no se ha encontrado");
         };
            
        // Busca el id y obtiene la info
        Sale sale = SaleFound.get();
            
        List<SaleDetailResponseDTO> details = new ArrayList<>();

        for (SaleDetail d : saleDetailRepository.findBySaleId(Id)) {
            SaleDetailResponseDTO detail = new SaleDetailResponseDTO();
            detail.setId(d.getId());
            detail.setAmount(d.getAmount());
            detail.setUnitPrice(d.getUnitPrice());
            detail.setSubtotal(d.getSubtotal());
            detail.setIdProduct(d.getProduct().getId());
            detail.setIdSale(d.getSale().getId());
            details.add(detail);
        }

        SaleResponseDTO response = new SaleResponseDTO();
        response.setId(sale.getId());
        response.setSaleDate(sale.getSaleDate());
        response.setTotal(sale.getTotal());
        response.setIdClient(sale.getIdClient().getId());
        response.setDetails(details);
        
        return response;
    }

    // Borrar venta
    @Transactional 
    public void deleteSale(Long Id) throws Exception{

        // Revision de si existe la venta
        Optional<Sale> saleFound = saleRepository.findById(Id);
        if (saleFound.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada o Id inexistente");
        }

        // Busca los detalles de la venta
        List<SaleDetail> details = saleDetailRepository.findBySaleId(Id);

        // Devuelve el stock de cada producto
        for (SaleDetail detail : details) {
            Product product = detail.getProduct();
            product.setStock(product.getStock() + detail.getAmount());
            productRepository.save(product);
        }

        // Borra primero los detalles y luego la venta
        saleDetailRepository.deleteAll(details);
        saleRepository.deleteById(Id);

    }

    // Clase que lista las ventas por cliente
    public List<SaleResponseDTO> getByClient(Long idClient) {
        List<SaleResponseDTO> response = new ArrayList<>();
        List<Sale> salesClient = saleRepository.findByIdClientId(idClient);

        for(Sale saleclient : salesClient){
            
            List<SaleDetailResponseDTO> details = new ArrayList<>();

            for (SaleDetail d : saleDetailRepository.findBySaleId(saleclient.getId())) {
                SaleDetailResponseDTO detail = new SaleDetailResponseDTO();
                detail.setId(d.getId());
                detail.setAmount(d.getAmount());
                detail.setUnitPrice(d.getUnitPrice());
                detail.setSubtotal(d.getSubtotal());
                detail.setIdProduct(d.getProduct().getId());
                detail.setIdSale(d.getSale().getId());
                details.add(detail);
            }

            SaleResponseDTO sale = new SaleResponseDTO();
            sale.setId(saleclient.getId());
            sale.setSaleDate(saleclient.getSaleDate());
            sale.setTotal(saleclient.getTotal());
            sale.setIdClient(saleclient.getIdClient().getId());
            sale.setDetails(details);

            response.add(sale);
        }

        return response;
    }

    // Clase que busca ventar por rango de fechas
    public List<SaleResponseDTO> getByDate(LocalDate start, LocalDate end) {
        List<SaleResponseDTO> response = new ArrayList<>();
        List<Sale> salesClient = saleRepository.findBySaleDateBetween(start.atStartOfDay(), end.atTime(LocalTime.MAX));

        for(Sale saleclient : salesClient){
            
            List<SaleDetailResponseDTO> details = new ArrayList<>();

            for (SaleDetail d : saleDetailRepository.findBySaleId(saleclient.getId())) {
                SaleDetailResponseDTO detail = new SaleDetailResponseDTO();
                detail.setId(d.getId());
                detail.setAmount(d.getAmount());
                detail.setUnitPrice(d.getUnitPrice());
                detail.setSubtotal(d.getSubtotal());
                detail.setIdProduct(d.getProduct().getId());
                detail.setIdSale(d.getSale().getId());
                details.add(detail);
            }

            SaleResponseDTO sale = new SaleResponseDTO();
            sale.setId(saleclient.getId());
            sale.setSaleDate(saleclient.getSaleDate());
            sale.setTotal(saleclient.getTotal());
            sale.setIdClient(saleclient.getIdClient().getId());
            sale.setDetails(details);

            response.add(sale);
        }

        return response;
    }
}
