package com.palpai.palpaibackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();

        // Add User-Agent header required by Wikipedia API
        ClientHttpRequestInterceptor userAgentInterceptor = (request, body, execution) -> {
            request.getHeaders().add("User-Agent", "PalpAI/1.0 (Learning Platform; bogdanskochynskyi@gmail.com)");
            return execution.execute(request, body);
        };

        template.setInterceptors(List.of(userAgentInterceptor));
        return template;
    }
}
