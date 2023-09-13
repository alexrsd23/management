package com.rosendo.company.Controllers.Application.General;

import com.rosendo.company.Domain.General.Sales.ConsultSalesDTO;
import com.rosendo.company.Domain.General.Sales.UpdateSalesDTO;
import com.rosendo.company.Entity.General.Sales;
import com.rosendo.company.Services.General.SalesService;
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
@RequestMapping("/sales")
@Tag(name = "Vendas")
public class SalesController {
    private final SalesService salesService;


    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de vendas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de objetos encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Lista de objetos não encontrada")
    })
    public ResponseEntity<List<ConsultSalesDTO>> findAllSales(){
        ModelMapper modelMapper = new ModelMapper();
        List<Sales> salesList = salesService.salesList();

        List<ConsultSalesDTO> dtoList = salesList.stream()
                .map(sales -> modelMapper.map(sales, ConsultSalesDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    @Operation(summary = "Registra uma nova venda no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado - objeto criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultSalesDTO> registerNewSales(@RequestBody UpdateSalesDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Sales sales= modelMapper.map(dto, Sales.class);
        salesService.saveSales(sales);
        ConsultSalesDTO registeredSales = modelMapper.map(sales, ConsultSalesDTO.class);
        return ResponseEntity.ok(registeredSales);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma venda registrada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultSalesDTO> findSales(@PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Sales sales = salesService.getIdSales(id);
        return ResponseEntity.ok(modelMapper.map(sales, ConsultSalesDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma venda específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultSalesDTO> updateNewSales(@RequestBody UpdateSalesDTO dto, @PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Sales originalSales = salesService.getIdSales(id);
        modelMapper.map(dto, originalSales);
        Sales updatedSales = salesService.saveSales(originalSales);
        ConsultSalesDTO updatedSalesDTO = modelMapper.map(updatedSales, ConsultSalesDTO.class);
        return ResponseEntity.ok(updatedSalesDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma venda específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum conteúdo - Objeto excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultSalesDTO> deleteSales(@PathVariable Long id) throws Exception {
        salesService.deleteSales(id);
        return ResponseEntity.ok().build();
    }
}
