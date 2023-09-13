package com.rosendo.company.Controllers.Application.General;

import com.rosendo.company.Domain.General.Supplier.ConsultSupplierDTO;
import com.rosendo.company.Domain.General.Supplier.UpdateSupplierDTO;
import com.rosendo.company.Entity.General.Supplier;
import com.rosendo.company.Services.General.SupplierService;
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
@RequestMapping("/supplier")
@Tag(name = "Fornecedores")
public class SupplierController {
    private final SupplierService supplierService;


    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de fornecedores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de objetos encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Lista de objetos não encontrada")
    })
    public ResponseEntity<List<ConsultSupplierDTO>> findAllSupplier(){
        ModelMapper modelMapper = new ModelMapper();
        List<Supplier> supplierList = supplierService.supplierList();

        List<ConsultSupplierDTO> dtoList = supplierList.stream()
                .map(supplier -> modelMapper.map(supplier, ConsultSupplierDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    @Operation(summary = "Registra um novo fornecedor no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado - objeto criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultSupplierDTO> registerNewSupplier(@RequestBody UpdateSupplierDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Supplier supplier= modelMapper.map(dto, Supplier.class);
        supplierService.saveSupplier(supplier);
        ConsultSupplierDTO registeredSupplier = modelMapper.map(supplier, ConsultSupplierDTO.class);
        return ResponseEntity.ok(registeredSupplier);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um fornecedor registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultSupplierDTO> findSupplier(@PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Supplier supplier = supplierService.getIdSupplier(id);
        return ResponseEntity.ok(modelMapper.map(supplier, ConsultSupplierDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um fornecedor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultSupplierDTO> updateNewSupplier(@RequestBody UpdateSupplierDTO dto, @PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Supplier originalSupplier = supplierService.getIdSupplier(id);
        modelMapper.map(dto, originalSupplier);
        Supplier updatedSupplier = supplierService.saveSupplier(originalSupplier);
        ConsultSupplierDTO updatedSupplierDTO = modelMapper.map(updatedSupplier, ConsultSupplierDTO.class);
        return ResponseEntity.ok(updatedSupplierDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um fornecedor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum conteúdo - Objeto excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultSupplierDTO> deleteSupplier(@PathVariable Long id) throws Exception {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok().build();
    }
}
