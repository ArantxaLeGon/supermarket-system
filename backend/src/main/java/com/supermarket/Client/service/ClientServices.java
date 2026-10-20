package com.supermarket.Client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.supermarket.Client.dto.ClientRequestDTO;
import com.supermarket.Client.dto.ClientResponseDTO;
import com.supermarket.Client.entity.Client;
import com.supermarket.Client.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ClientServices {
    private final ClientRepository clientRepository;

        // Clase que crea el metodo de crear clientes
    public ClientResponseDTO crearteClient(ClientRequestDTO request) throws Exception{
        // Revision de si exite el nombre
        Optional<Client> clientfound = clientRepository.findByDocument(request.getDocument());
        
        // Si existe, parece error
        if(clientfound.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Documento ya esta en uso");
        }

        // Reistra lo ingresado
        Client client = new Client();
        client.setName(request.getName());
        client.setDocument(request.getDocument());
        client.setPhone(request.getPhone());
        clientRepository.save(client);

        // Lo trasforma en response
        ClientResponseDTO response = new ClientResponseDTO();
        response.setId(client.getId());
        response.setName(client.getName());
        response.setDocument(client.getDocument());
        response.setPhone(client.getPhone());

        return response;
    };

    // Clase que llama a todos las clientes
    public List<ClientResponseDTO> getallClient(){
        
        // Crea un array para todos las clientes
        List<ClientResponseDTO> response = new ArrayList<>();

        // Busca todos los registros
        List<Client> categoriesfound = clientRepository.findAll();
        
        // Registro por registro los añade al array
        for(Client clientfound: categoriesfound){

            // Pasa la informacion al response
            ClientResponseDTO client = new ClientResponseDTO();
            client.setId(clientfound.getId());
            client.setName(clientfound.getName());
            client.setDocument(clientfound.getDocument());
            client.setPhone(clientfound.getPhone());

            response.add(client);
        }

        return response;
    }

    // Clase que llama a una categoria por ID
    public ClientResponseDTO getIdClient(Long Id) throws Exception {
        Optional<Client> clientFound = clientRepository.findById(Id);

        // Validacion si se encuentra error
        if(clientFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "No se encontro el cliente");
        }

        // Busca el id y obtene la info
        Client client = clientFound.get();

        ClientResponseDTO response = new ClientResponseDTO();
        response.setId(client.getId());
        response.setName(client.getName());
        response.setDocument(client.getDocument());
        response.setPhone(client.getPhone());

        return response;
    }

    // Clase que actualiza la informacion de un ID
    public ClientResponseDTO putClient(Long Id, ClientRequestDTO request) throws Exception{
        
        Optional<Client> clientIdFound = clientRepository.findById(Id);
        if(clientIdFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "No se encontro el cliente");
        }
        
        Optional<Client> clientDocumentFound = clientRepository.findByDocument(request.getDocument());
        if(clientDocumentFound.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Cliente ya existente");
        }

        Client client = clientIdFound.get();
        client.setName(request.getName());
        client.setDocument(request.getDocument());
        client.setPhone(request.getPhone());
        clientRepository.save(client);

        ClientResponseDTO response = new ClientResponseDTO();
        response.setId(client.getId());
        response.setName(client.getName());
        response.setDocument(client.getDocument());
        response.setPhone(client.getPhone());

        return response;
    }

    public void deleteClient(Long Id) throws Exception{

       if (clientRepository.existsById(Id)){
            clientRepository.deleteById(Id);
       } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "No se encontro el cliente");
       }
    }
}
