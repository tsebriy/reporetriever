package com.nortal.reporetriever.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class GitHubServiceTests {

    @Autowired
    private GitHubRepositoryService service;

    @Autowired
    private WebTestClient webTestClient;

    @Test//TODO: use wiremock
    void getDataSuccess() {
//        StepVerifier.create(service.getRepositories("JohnSmith"))
//                .assertNext(repo -> assertEquals("awesomeProject", repo.name()));
    }
}
