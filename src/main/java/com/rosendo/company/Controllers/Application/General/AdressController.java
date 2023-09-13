package com.rosendo.company.Controllers.Application.General;

import com.rosendo.company.Domain.General.Adress.ConsultAdressDTO;
import com.rosendo.company.Domain.General.Adress.UpdateAdressDTO;
import com.rosendo.company.Entity.General.Adress;
import com.rosendo.company.Services.General.AdressService;
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
@RequestMapping("/adress")
@Tag(name = "Endereços")
public class AdressController {
    private final AdressService adressService;


    public AdressController(AdressService adressService) {
        this.adressService = adressService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de endereços registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de objetos encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Lista de objetos não encontrada")
    })
    public ResponseEntity<List<ConsultAdressDTO>> findAllAdress(){
        ModelMapper modelMapper = new ModelMapper();
        List<Adress> adressList = adressService.adressList();

        List<ConsultAdressDTO> dtoList = adressList.stream()
                .map(adress -> modelMapper.map(adress, ConsultAdressDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    @Operation(summary = "Registra um novo endereço no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado - objeto criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultAdressDTO> registerNewAdress(@RequestBody UpdateAdressDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Adress adress= modelMapper.map(dto, Adress.class);
        adressService.saveAdress(adress);
        ConsultAdressDTO registeredAdress = modelMapper.map(adress, ConsultAdressDTO.class);
        return ResponseEntity.ok(registeredAdress);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um endereço registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultAdressDTO> findAdress(@PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Adress adress = adressService.getIdAdress(id);
        return ResponseEntity.ok(modelMapper.map(adress, ConsultAdressDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um endereço específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultAdressDTO> updateNewAdress(@RequestBody UpdateAdressDTO dto, @PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Adress originalAdress = adressService.getIdAdress(id);
        modelMapper.map(dto, originalAdress);
        Adress updatedAdress = adressService.saveAdress(originalAdress);
        ConsultAdressDTO updatedAdressDTO = modelMapper.map(updatedAdress, ConsultAdressDTO.class);
        return ResponseEntity.ok(updatedAdressDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um endereço específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum conteúdo - Objeto excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultAdressDTO> deleteAdress(@PathVariable Long id) throws Exception {
        adressService.deleteAdress(id);
        return ResponseEntity.ok().build();
    }


}
