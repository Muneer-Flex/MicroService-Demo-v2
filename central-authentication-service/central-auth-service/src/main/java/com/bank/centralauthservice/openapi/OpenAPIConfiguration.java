package com.bank.centralauthservice.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact();
        contact.setEmail("Muneer@gmail.com");
        contact.setName("Muneer Ahmed J");
        contact.setUrl("https://www.google.com");

        License license = new License();
        license.setName("Open Source License");
        license.setUrl("https://www.google.com");

        Info info = new Info()
                .contact(contact)
                .license(license)
                .title("Central Authentication Service")
                .version("1.0")
                .description("Central Authentication Management Service")
                .termsOfService("Central Auth Terms & Conditions");

        return new OpenAPI().info(info);
    }
}
