package ptithcm.tttn.config;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {


    private String clientId = "AYz0m2t0hkKiyrQIwoYUOHyCvDtIgtqb3eqZy5AWeUh4Bpb5QnjNW1ZL7lrIH3Zno2gg7qkW3RSxHkim";

    private String clientSecret = "EJkww2OpT2hsyyYkwKcxbG2oz5lOUOPf2y-fzdcxzCQk6SFUtanr7_HGMwN6bYWbTEWBk1pOvwGXWqkO";

    private String mode = "sandbox";

    @Bean
    public APIContext apiContext(){
        return new APIContext(clientId, clientSecret, mode);
    }
}
