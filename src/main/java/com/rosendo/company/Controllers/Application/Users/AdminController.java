package com.rosendo.company.Controllers.Application.Users;

import com.rosendo.company.Domain.Users.Admin.ConsultAdminDTO;
import com.rosendo.company.Domain.Users.Admin.UpdateAdminDTO;
import com.rosendo.company.Entity.Users.Admin;
import com.rosendo.company.Services.Users.AdminService;
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
@RequestMapping("/admin")
@Tag(name = "Administradores")
public class AdminController {
    private final AdminService adminService;


    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de administradores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de objetos encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Lista de objetos não encontrada")
    })
    public ResponseEntity<List<ConsultAdminDTO>> findAllAdmin(){
        ModelMapper modelMapper = new ModelMapper();
        List<Admin> adminList = adminService.adminList();

        List<ConsultAdminDTO> dtoList = adminList.stream()
                .map(admin -> modelMapper.map(admin, ConsultAdminDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    @Operation(summary = "Registra um novo administrador no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado - objeto criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultAdminDTO> registerNewAdmin(@RequestBody UpdateAdminDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Admin admin= modelMapper.map(dto, Admin.class);
        adminService.saveAdmin(admin);
        ConsultAdminDTO registeredAdmin = modelMapper.map(admin, ConsultAdminDTO.class);
        return ResponseEntity.ok(registeredAdmin);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um administrador registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultAdminDTO> findAdmin(@PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Admin admin = adminService.getIdAdmin(id);
        return ResponseEntity.ok(modelMapper.map(admin, ConsultAdminDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um administrador específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultAdminDTO> updateNewAdmin(@RequestBody UpdateAdminDTO dto, @PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Admin originalAdmin = adminService.getIdAdmin(id);
        modelMapper.map(dto, originalAdmin);
        Admin updatedAdmin = adminService.saveAdmin(originalAdmin);
        ConsultAdminDTO updatedAdminDTO = modelMapper.map(updatedAdmin, ConsultAdminDTO.class);
        return ResponseEntity.ok(updatedAdminDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um administrador específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum conteúdo - Objeto excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultAdminDTO> deleteAdmin(@PathVariable Long id) throws Exception {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok().build();
    }
}
