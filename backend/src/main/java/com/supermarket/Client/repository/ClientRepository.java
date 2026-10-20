package com.supermarket.Client.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermarket.Client.entity.Client;

public interface ClientRepository extends JpaRepository<Client,Long>{
    Optional<Client>findByDocument(String document);

    boolean existsById (Long IdClient);

}
