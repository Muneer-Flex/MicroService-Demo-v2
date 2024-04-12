package com.bank.governingstate.openapi;

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
        contact.setEmail("muneer@gmail.com");

        License license = new License();
        license.setName("Open Source License");
        license.setUrl("https://www.google.com/");

        Info info = new Info()
                .contact(contact)
                .license(license)
                .description("Governing State Management Services")
                .termsOfService("Governing State Terms & Conditions Apply")
                .title("Governing State Services")
                .version("1.0");

        return new OpenAPI().info(info);
    }
}
