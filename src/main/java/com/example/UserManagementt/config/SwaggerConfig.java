package com.example.UserManagementt.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("User Management API")
                    .version("1.0")
                    .description("API documentation for User Management System")
                    .contact(new Contact()
                            .name("Choirunnisa")
                            .email("choirunnisaa32@gmail.com")
                            .url("@https://choirunnisa12.github.io/portfolio_choirunnisa/")
                    )
            );
}
}