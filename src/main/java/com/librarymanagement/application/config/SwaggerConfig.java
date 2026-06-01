package com.librarymanagement.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI app() {
        final Contact coreEngineeringContact = new Contact();
        coreEngineeringContact.setEmail("balashan.1999@gmail.com");
        coreEngineeringContact.setName("Balashanmugam Ravichelvan");

        return new OpenAPI()
                .info(new Info()
                        .title("Library Management")
                        .version("1.0.0")
                        .description("This Application manages records of books borrowed by maintaining books and members data.")
                        .contact(coreEngineeringContact));
    }

}
