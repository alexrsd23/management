package com.rosendo.company.Controllers.Application.General;

import com.rosendo.company.Domain.General.Brand.ConsultBrandDTO;
import com.rosendo.company.Domain.General.Brand.UpdateBrandDTO;
import com.rosendo.company.Entity.General.Brand;
import com.rosendo.company.Services.General.BrandService;
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
@RequestMapping("/brand")
@Tag(name = "Marcas")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de marcas registradas")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de objetos encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Lista de objetos não encontrada")
    })
    public ResponseEntity<List<ConsultBrandDTO>> findAllBrand(){
        ModelMapper modelMapper = new ModelMapper();
        List<Brand> brandList = brandService.brandList();

        List<ConsultBrandDTO> dtoList = brandList.stream()
                .map(brand -> modelMapper.map(brand, ConsultBrandDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    @Operation(summary = "Registra uma nova marca no sistema")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado - objeto criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultBrandDTO> registerNewBrand(@RequestBody UpdateBrandDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Brand brand = modelMapper.map(dto, Brand.class);
        brandService.saveBrand(brand);
        ConsultBrandDTO registeredBrand = modelMapper.map(brand, ConsultBrandDTO.class);
        return ResponseEntity.ok(registeredBrand);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma marca registrada")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultBrandDTO> finBrand(@PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Brand brand = brandService.getIdBrand(id);
        return ResponseEntity.ok(modelMapper.map(brand, ConsultBrandDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma marca especifica")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultBrandDTO> updateBrand (@RequestBody UpdateBrandDTO dto, @PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Brand originalBrand = brandService.getIdBrand(id);
        modelMapper.map(dto, originalBrand);
        Brand updatedBrand = brandService.saveBrand(originalBrand);
        ConsultBrandDTO updatedBrandDTO = modelMapper.map(updatedBrand, ConsultBrandDTO.class);
        return ResponseEntity.ok(updatedBrandDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma marca especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum conteúdo - Objeto excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultBrandDTO> deleteBrand(@PathVariable Long id)throws Exception{
        brandService.deleteBrand(id);
        return ResponseEntity.ok().build();
    }
}
