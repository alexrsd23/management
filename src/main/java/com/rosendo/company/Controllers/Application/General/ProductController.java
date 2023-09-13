package com.rosendo.company.Controllers.Application.General;

import com.rosendo.company.Domain.General.Product.ConsultProductDTO;
import com.rosendo.company.Domain.General.Product.UpdateProductDTO;
import com.rosendo.company.Entity.General.Product;
import com.rosendo.company.Services.General.ProductService;
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
@RequestMapping("/product")
@Tag(name = "Produtos")
public class ProductController {
    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Retorna uma lista de produtos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de objetos encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Lista de objetos não encontrada")
    })
    public ResponseEntity<List<ConsultProductDTO>> findAllProduct(){
        ModelMapper modelMapper = new ModelMapper();
        List<Product> productList = productService.productList();

        List<ConsultProductDTO> dtoList = productList.stream()
                .map(product -> modelMapper.map(product, ConsultProductDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    @Operation(summary = "Registra um novo produto no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado - objeto criado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultProductDTO> registerNewProduct(@RequestBody UpdateProductDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Product product= modelMapper.map(dto, Product.class);
        productService.saveProduct(product);
        ConsultProductDTO registeredProduct = modelMapper.map(product, ConsultProductDTO.class);
        return ResponseEntity.ok(registeredProduct);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna um produto registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultProductDTO> findProduct(@PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Product product = productService.getIdProduct(id);
        return ResponseEntity.ok(modelMapper.map(product, ConsultProductDTO.class));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um produto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Objeto atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida - Dados ausentes ou inválidos")
    })
    public ResponseEntity<ConsultProductDTO> updateNewProduct(@RequestBody UpdateProductDTO dto, @PathVariable Long id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Product originalProduct = productService.getIdProduct(id);
        modelMapper.map(dto, originalProduct);
        Product updatedProduct = productService.saveProduct(originalProduct);
        ConsultProductDTO updatedProductDTO = modelMapper.map(updatedProduct, ConsultProductDTO.class);
        return ResponseEntity.ok(updatedProductDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um produto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum conteúdo - Objeto excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Falha na autenticação"),
            @ApiResponse(responseCode = "404", description = "Não encontrado - Objeto não encontrado")
    })
    public ResponseEntity<ConsultProductDTO> deleteProduct(@PathVariable Long id) throws Exception {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

}
