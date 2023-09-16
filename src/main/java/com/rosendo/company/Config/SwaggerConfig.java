package com.rosendo.company.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;


@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de gerenciamento de vendas")
                        .description("Uma API simples para controle de vendas")
                        .version("1.0.0")
                        .contact(new Contact().name("Alex Rosendo").url("https://www.linkedin.com/in/alex-rosendo/").email("alexrosendo23@outlook.com")))
                .tags(Arrays.asList(
                        new Tag().name("Endereços").description("Operações relacionadas aos endereços"),
                        new Tag().name("Categorias").description("Operações relacionadas as categorias"),
                        new Tag().name("Marcas").description("Operações relacionadas as marcas"),
                        new Tag().name("Unidades").description("Operações relacionadas as unidades"),
                        new Tag().name("Venda de Itens").description("Operações relacionadas as vendas de itens"),
                        new Tag().name("Vendas").description("Operações relacionadas as vendas"),
                        new Tag().name("Produtos").description("Operações relacionadas aos produtos"),
                        new Tag().name("Administradores").description("Operações relacionadas aos administradores"),
                        new Tag().name("Gerentes").description("Operações relacionadas aos gerentes"),
                        new Tag().name("Clientes").description("Operações relacionadas aos administradores"),
                        new Tag().name("Fornecedores").description("Operações relacionadas aos fornecedores"),
                        new Tag().name("Vendedores").description("Operações relacionadas aos vendedores")));
    }

}


