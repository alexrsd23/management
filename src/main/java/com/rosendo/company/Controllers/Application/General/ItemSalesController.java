package com.rosendo.company.Controllers.Application.General;

import com.rosendo.company.Domain.General.ItemSales.ConsultItemSalesDTO;
import com.rosendo.company.Domain.General.ItemSales.UpdateItemSalesDTO;
import com.rosendo.company.Entity.General.ItemSales;
import com.rosendo.company.Services.General.ItemSalesService;
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
@RequestMapping("/itemsales")
@Tag(name = "Venda de Itens")
public class ItemSalesController {
    private final ItemSalesService itemSalesService;


    public ItemSalesController(ItemSalesService itemSalesService) {
        this.itemSalesService = itemSalesService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de itens vendidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de objetos encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Lista de objetos não encontrada")
    })
    public ResponseEntity<List<ConsultItemSalesDTO>> findAllItemSales(){
        ModelMapper modelMapper = new ModelMapper();
        List<ItemSales> itemSalesList = itemSalesService.itemSalesList();

        List<ConsultItemSalesDTO> dtoList = itemSalesList.stream()
                .map(itemSales -> modelMapper.map(itemSales, ConsultItemSalesDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    @Operation(summary = "Registra uma nova venda de itens no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado - objeto criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultItemSalesDTO> registerNewItemSales(@RequestBody UpdateItemSalesDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        ItemSales itemSales= modelMapper.map(dto, ItemSales.class);
        itemSalesService.saveItemSales(itemSales);
        ConsultItemSalesDTO registeredItemSales = modelMapper.map(itemSales, ConsultItemSalesDTO.class);
        return ResponseEntity.ok(registeredItemSales);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma venda de item especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultItemSalesDTO> findItemSales(@PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        ItemSales itemSales = itemSalesService.getIdItemSales(id);
        return ResponseEntity.ok(modelMapper.map(itemSales, ConsultItemSalesDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma venda de item especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultItemSalesDTO> updateNewItemSales(@RequestBody UpdateItemSalesDTO dto, @PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        ItemSales originalItemSales = itemSalesService.getIdItemSales(id);
        modelMapper.map(dto, originalItemSales);
        ItemSales updatedItemSales = itemSalesService.saveItemSales(originalItemSales);
        ConsultItemSalesDTO updatedItemSalesDTO = modelMapper.map(updatedItemSales, ConsultItemSalesDTO.class);
        return ResponseEntity.ok(updatedItemSalesDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma venda de item especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum conteúdo - Objeto excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultItemSalesDTO> deleteItemSales(@PathVariable Long id) throws Exception {
        itemSalesService.deleteItemSales(id);
        return ResponseEntity.ok().build();
    }
}
