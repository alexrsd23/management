package com.rosendo.company.Controllers.Application.Users;

import com.rosendo.company.Domain.Users.Manager.ConsultManagerDTO;
import com.rosendo.company.Domain.Users.Manager.UpdateManagerDTO;
import com.rosendo.company.Entity.Users.Manager;
import com.rosendo.company.Services.Users.ManagerService;
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
@RequestMapping("/manager")
@Tag(name = "Gerentes")
public class ManagerController {
    private final ManagerService managerService;


    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de gerentes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de objetos encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Lista de objetos não encontrada")
    })
    public ResponseEntity<List<ConsultManagerDTO>> findAllManager(){
        ModelMapper modelMapper = new ModelMapper();
        List<Manager> managerList = managerService.managerList();

        List<ConsultManagerDTO> dtoList = managerList.stream()
                .map(manager -> modelMapper.map(manager, ConsultManagerDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    @Operation(summary = "Registra um novo gerente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado - objeto criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultManagerDTO> registerNewManager(@RequestBody UpdateManagerDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Manager manager= modelMapper.map(dto, Manager.class);
        managerService.saveManager(manager);
        ConsultManagerDTO registeredManager = modelMapper.map(manager, ConsultManagerDTO.class);
        return ResponseEntity.ok(registeredManager);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um gerente registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultManagerDTO> findManager(@PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Manager manager = managerService.getIdManager(id);
        return ResponseEntity.ok(modelMapper.map(manager, ConsultManagerDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um gerente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultManagerDTO> updateNewManager(@RequestBody UpdateManagerDTO dto, @PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Manager originalManager = managerService.getIdManager(id);
        modelMapper.map(dto, originalManager);
        Manager updatedManager = managerService.saveManager(originalManager);
        ConsultManagerDTO updatedManagerDTO = modelMapper.map(updatedManager, ConsultManagerDTO.class);
        return ResponseEntity.ok(updatedManagerDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um gerente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum conteúdo - Objeto excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultManagerDTO> deleteManager(@PathVariable Long id) throws Exception {
        managerService.deleteManager(id);
        return ResponseEntity.ok().build();
    }
}
