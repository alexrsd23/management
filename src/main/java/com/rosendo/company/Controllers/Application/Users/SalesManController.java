package com.rosendo.company.Controllers.Application.Users;

import com.rosendo.company.Domain.Users.SalesMan.ConsultSalesManDTO;
import com.rosendo.company.Domain.Users.SalesMan.UpdateSalesManDTO;
import com.rosendo.company.Entity.Users.SalesMan;
import com.rosendo.company.Services.Users.SalesManService;
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
@RequestMapping("/salesman")
@Tag(name = "Vendedores")
public class SalesManController {
    private final SalesManService salesManService;


    public SalesManController(SalesManService salesManService) {
        this.salesManService = salesManService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de vendedores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de objetos encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Lista de objetos não encontrada")
    })
    public ResponseEntity<List<ConsultSalesManDTO>> findAllSalesMan(){
        ModelMapper modelMapper = new ModelMapper();
        List<SalesMan> salesManList = salesManService.salesManList();

        List<ConsultSalesManDTO> dtoList = salesManList.stream()
                .map(salesMan -> modelMapper.map(salesMan, ConsultSalesManDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    @Operation(summary = "Registra um novo vendedor no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado - objeto criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultSalesManDTO> registerNewSalesMan(@RequestBody UpdateSalesManDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        SalesMan salesMan= modelMapper.map(dto, SalesMan.class);
        salesManService.saveSalesMan(salesMan);
        ConsultSalesManDTO registeredSalesMan = modelMapper.map(salesMan, ConsultSalesManDTO.class);
        return ResponseEntity.ok(registeredSalesMan);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um vendedor registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultSalesManDTO> findSalesMan(@PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        SalesMan salesMan = salesManService.getIdSalesMan(id);
        return ResponseEntity.ok(modelMapper.map(salesMan, ConsultSalesManDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um vendedor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultSalesManDTO> updateNewSalesMan(@RequestBody UpdateSalesManDTO dto, @PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        SalesMan originalSalesMan = salesManService.getIdSalesMan(id);
        modelMapper.map(dto, originalSalesMan);
        SalesMan updatedSalesMan = salesManService.saveSalesMan(originalSalesMan);
        ConsultSalesManDTO updatedSalesManDTO = modelMapper.map(updatedSalesMan, ConsultSalesManDTO.class);
        return ResponseEntity.ok(updatedSalesManDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um vendedor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum conteúdo - Objeto excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultSalesManDTO> deleteSalesMan(@PathVariable Long id) throws Exception {
        salesManService.deleteSalesMan(id);
        return ResponseEntity.ok().build();
    }
}
