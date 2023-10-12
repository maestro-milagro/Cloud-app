package maestro.milagro.UserService.config;

import maestro.milagro.UserService.client.JWTClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;


    @Bean
    public WebClient JWTWebClient() {
        return WebClient.builder()
                .baseUrl("http://JWT-Service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public JWTClient employeeClient() {
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(JWTWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(JWTClient.class);
    }
}
