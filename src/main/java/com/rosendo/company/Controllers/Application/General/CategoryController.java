package com.rosendo.company.Controllers.Application.General;

import com.rosendo.company.Domain.General.Category.ConsultCategoryDTO;
import com.rosendo.company.Domain.General.Category.UpdateCategoryDTO;
import com.rosendo.company.Entity.General.Category;
import com.rosendo.company.Services.General.CategoryService;
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
@RequestMapping("/category")
@Tag(name = "Categorias")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de categorias registradas")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de objetos encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Lista de objetos não encontrada")
    })
    public ResponseEntity<List<ConsultCategoryDTO>> findAllCategory(){
        ModelMapper modelMapper = new ModelMapper();
        List<Category> categoryList = categoryService.categoryList();

        List<ConsultCategoryDTO> dtoList = categoryList.stream()
                .map(category -> modelMapper.map(category, ConsultCategoryDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    @Operation(summary = "Registra uma nova categoria no sistema")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado - objeto criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultCategoryDTO> registerNewCategory(@RequestBody UpdateCategoryDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Category category = modelMapper.map(dto, Category.class);
        categoryService.saveCategory(category);
        ConsultCategoryDTO registeredCategory = modelMapper.map(category, ConsultCategoryDTO.class);
        return ResponseEntity.ok(registeredCategory);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna uma categoria registrada")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultCategoryDTO> finCategory(@PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Category category = categoryService.getIdCategory(id);
        return ResponseEntity.ok(modelMapper.map(category, ConsultCategoryDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma categoria especifica")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultCategoryDTO> updateCategory (@RequestBody UpdateCategoryDTO dto, @PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Category originalCategory = categoryService.getIdCategory(id);
        modelMapper.map(dto, originalCategory);
        Category updatedCategory = categoryService.saveCategory(originalCategory);
        ConsultCategoryDTO updatedCategoryDTO = modelMapper.map(updatedCategory, ConsultCategoryDTO.class);
        return ResponseEntity.ok(updatedCategoryDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma categoria especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum conteúdo - Objeto excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultCategoryDTO> deleteCategory(@PathVariable Long id)throws Exception{
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
