package com.rosendo.company.Controllers.Application.Users;

import com.rosendo.company.Domain.Users.Client.ConsultClientDTO;
import com.rosendo.company.Domain.Users.Client.UpdateClientDTO;
import com.rosendo.company.Entity.Users.Client;
import com.rosendo.company.Services.Users.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@Tag(name = "Clientes")
public class ClientController {
    private final ClientService clientService;


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de clientes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de objetos encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Lista de objetos não encontrada")
    })
    public ResponseEntity<List<ConsultClientDTO>> findAllClient(){
        ModelMapper modelMapper = new ModelMapper();
        List<Client> clientList = clientService.clientList();

        List<ConsultClientDTO> dtoList = clientList.stream()
                .map(client -> modelMapper.map(client, ConsultClientDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    @Operation(summary = "Registra um novo cliente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado - objeto criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultClientDTO> registerNewClient(@RequestBody UpdateClientDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Client client= modelMapper.map(dto, Client.class);
        clientService.saveClient(client);
        ConsultClientDTO registeredClient = modelMapper.map(client, ConsultClientDTO.class);
        return ResponseEntity.ok(registeredClient);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um cliente registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultClientDTO> findClient(@PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Client client = clientService.getIdClient(id);
        return ResponseEntity.ok(modelMapper.map(client, ConsultClientDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultClientDTO> updateNewClient(@RequestBody UpdateClientDTO dto, @PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Client originalClient = clientService.getIdClient(id);
        modelMapper.map(dto, originalClient);
        Client updatedClient = clientService.saveClient(originalClient);
        ConsultClientDTO updatedClientDTO = modelMapper.map(updatedClient, ConsultClientDTO.class);
        return ResponseEntity.ok(updatedClientDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum conteúdo - Objeto excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultClientDTO> deleteClient(@PathVariable Long id) throws Exception {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }
}
