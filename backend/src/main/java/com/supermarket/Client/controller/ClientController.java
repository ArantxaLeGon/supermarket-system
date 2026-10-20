package com.supermarket.Client.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.Client.dto.ClientRequestDTO;
import com.supermarket.Client.dto.ClientResponseDTO;
import com.supermarket.Client.service.ClientServices;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController 
@RequiredArgsConstructor 
@RequestMapping("/Client")
public class ClientController {
    private final ClientServices clientService;

    @PostMapping("/Post")
    public ResponseEntity<ClientResponseDTO> crearteClient (@RequestBody ClientRequestDTO request) throws Exception{
        ClientResponseDTO response = clientService.crearteClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    };
    
    @GetMapping("/Get")
    public ResponseEntity<List<ClientResponseDTO>> getallClient() {
        List<ClientResponseDTO> response = clientService.getallClient();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/Get/{id}")
    public ResponseEntity<ClientResponseDTO> getIdClient (@PathVariable Long id) throws Exception{
        ClientResponseDTO response = clientService.getIdClient(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/Put/{id}")
    public ResponseEntity<ClientResponseDTO> putClient(@PathVariable Long id, @RequestBody ClientRequestDTO request) throws Exception {
        ClientResponseDTO response = clientService.putClient(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
     @DeleteMapping("/Delete/{id}")
     public ResponseEntity<Void> deleteClient(@PathVariable Long id) throws Exception{
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
     }
     
}
