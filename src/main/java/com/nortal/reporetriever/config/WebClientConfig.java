package com.nortal.reporetriever.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(@Value("${git.hub.api.url}") String url, @Value("${git.hub.api.token}") String token) {
        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader("Authorization", "token " + token)
                .build();
    }
}

