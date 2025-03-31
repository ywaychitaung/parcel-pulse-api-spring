package com.parcelpulseapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl("https://parcelpulse-api.example.com");
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setName("ParcelPulse API Support");
        contact.setEmail("support@parcelpulse.example.com");
        contact.setUrl("https://www.parcelpulse.example.com");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("ParcelPulse API Documentation")
                .version("1.0.0")
                .contact(contact)
                .description("This API exposes endpoints for the ParcelPulse application.")
                .license(mitLicense);
                
        // Define the security scheme
        Components components = new Components()
                .addSecuritySchemes("bearerAuth", 
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("Enter JWT token with Bearer prefix"));

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .components(components)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}