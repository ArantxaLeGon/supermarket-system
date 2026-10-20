package com.supermarket.Client.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermarket.Client.entity.Client;

public interface ClientRepository extends JpaRepository<Client,Long>{
    // Busca un cliente por documento
    Optional<Client>findByDocument(String document);

    // Verifica si existe el id del cliente (Usado en Sale)
    boolean existsById (Long IdClient);

}
