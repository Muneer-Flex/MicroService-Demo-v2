package com.bank.createcustomerprofile.openapi;

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
        contact.setName("Muneer Ahmed J");
        contact.setEmail("Muneer@gmail.com");
        contact.setUrl("https://www.google.com");

        License license = new License();
        license.setName("Open Source License");
        license.setUrl("https://www.google.com");

        Info info = new Info()
                .contact(contact)
                .license(license)
                .title("Create Customer Profile Service")
                .description("Create Customer Profile Management Service - Protected")
                .version("1.0")
                .termsOfService("Create Customer Profile Service - T&C");

        return new OpenAPI().info(info);
    }
}
