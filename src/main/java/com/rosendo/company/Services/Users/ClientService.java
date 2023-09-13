package com.rosendo.company.Services.Users;

import com.rosendo.company.Entity.Users.Client;
import com.rosendo.company.Repository.Users.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    public Client saveClient(Client client){
        return clientRepository.save(client);
    }

    public List<Client> clientList(){
        return clientRepository.findAll();
    }

    public Client getIdClient(Long id) throws Exception {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()){
            throw new Exception("Client not found!");
        }
        return client.get();
    }

    public Client updateClient(Client updatedClient, Long id) throws Exception {
        Client originalClient = this.getIdClient(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(updatedClient, originalClient);
        return clientRepository.save(originalClient);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
