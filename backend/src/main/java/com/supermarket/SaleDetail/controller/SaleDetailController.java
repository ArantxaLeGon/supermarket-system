package com.supermarket.SaleDetail.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.SaleDetail.dto.SaleDetailRequestDTO;
import com.supermarket.SaleDetail.dto.SaleDetailResponseDTO;
import com.supermarket.SaleDetail.service.SaleDetailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping ("api/saleDetail")
public class SaleDetailController {
    private final SaleDetailService saleDetailService;

    @PostMapping
    public ResponseEntity<SaleDetailResponseDTO> createSaleDetail (@RequestBody SaleDetailRequestDTO request) throws Exception{
        SaleDetailResponseDTO response = saleDetailService.createSaleDetail(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    };

    @GetMapping
    public ResponseEntity<List<SaleDetailResponseDTO>> getallSaleDetail() {
        List<SaleDetailResponseDTO> response = saleDetailService.getallSaleDetail();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDetailResponseDTO> getIdSaleDetail (@PathVariable Long id) throws Exception{
        SaleDetailResponseDTO response = saleDetailService.getIdSaleDetail(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Detalles de una venta
    @GetMapping("/sale/{saleId}")
    public ResponseEntity<List<SaleDetailResponseDTO>> getSaleDetailBySale (@PathVariable Long saleId) {
        List<SaleDetailResponseDTO> response = saleDetailService.getSaleDetailBySale(saleId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDetailResponseDTO> putSaleDetail(@PathVariable Long id, @RequestBody SaleDetailRequestDTO request) throws Exception {
        SaleDetailResponseDTO response = saleDetailService.putSaleDetail(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleDetail(@PathVariable Long id) throws Exception{
        saleDetailService.deleteSaleDetail(id);
        return ResponseEntity.noContent().build();
    }
}
