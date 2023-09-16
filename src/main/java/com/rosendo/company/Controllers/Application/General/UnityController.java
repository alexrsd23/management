package com.rosendo.company.Controllers.Application.General;

import com.rosendo.company.Domain.General.Unity.ConsultUnityDTO;
import com.rosendo.company.Domain.General.Unity.UpdateUnityDTO;
import com.rosendo.company.Entity.General.Unity;
import com.rosendo.company.Services.General.UnityService;
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
@RequestMapping("/unity")
@Tag(name = "Unidades")
public class UnityController {
    private final UnityService unityService;

    public UnityController(UnityService unityService) {
        this.unityService = unityService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de unidades registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de objetos encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Lista de objetos não encontrada")
    })
    public ResponseEntity<List<ConsultUnityDTO>> findAllUnity(){
        ModelMapper modelMapper = new ModelMapper();
        List<Unity> unityList = unityService.unityList();

        List<ConsultUnityDTO> dtoList = unityList.stream()
                .map(unity -> modelMapper.map(unity, ConsultUnityDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    @Operation(summary = "Registra uma nova unidade no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado - objeto criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultUnityDTO> registerNewUnity(@RequestBody UpdateUnityDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Unity unity = modelMapper.map(dto, Unity.class);
        unityService.saveUnity(unity);
        ConsultUnityDTO registeredUnity = modelMapper.map(unity, ConsultUnityDTO.class);
        return ResponseEntity.ok(registeredUnity);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma unidade registrada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultUnityDTO> finUnity(@PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Unity unity = unityService.getIdUnity(id);
        return ResponseEntity.ok(modelMapper.map(unity, ConsultUnityDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma unidade especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultUnityDTO> updateUnity (@RequestBody UpdateUnityDTO dto, @PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Unity originalUnity = unityService.getIdUnity(id);
        modelMapper.map(dto, originalUnity);
        Unity updatedUnity = unityService.saveUnity(originalUnity);
        ConsultUnityDTO updatedUnityDTO = modelMapper.map(updatedUnity, ConsultUnityDTO.class);
        return ResponseEntity.ok(updatedUnityDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma unidade especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum conteúdo - Objeto excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultUnityDTO> deleteUnity(@PathVariable Long id)throws Exception{
        unityService.deleteUnity(id);
        return ResponseEntity.ok().build();
    }
}
