package cz.cvut.fel.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cartServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("Cart Service API")
                        .description("This is the REST API for managing user cart and cart items")
                        .version("v1.0.0")
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("aivazart@fel.cvut.cz"))
                        .license(new License().name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(Arrays.asList(
                new Server().url("http://localhost:8010/cart-service").description("Local Development Server"),
                new Server().url("https://aivazart.com/cart-service").description("Production Server")
        ));
    }
}
