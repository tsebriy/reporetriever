package com.nortal.reporetriever.controller;

import com.nortal.reporetriever.model.dto.BranchDto;
import com.nortal.reporetriever.model.dto.OwnerDto;
import com.nortal.reporetriever.model.dto.RepositoryDto;
import com.nortal.reporetriever.service.GitHubRepositoryService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(controllers = RepositoryController.class)
public class RepoControllerTest {

    @MockBean
    private GitHubRepositoryService service;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testInvalidContentTypeThrowsException() {
        when(service.getRepositories("JonSmith"))
                .thenReturn(getTestRepositories());
        webClient.get().uri("/repository?username=JonSmith")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testGetConfigurationsSuccess() {
        when(service.getRepositories("JonSmith"))
                .thenReturn(getTestRepositories());
        webClient.get().uri("/repository?username=JonSmith")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RepositoryDto.class)
                .consumeWith(actual -> verifyGetResult(requireNonNull(actual.getResponseBody())));
        verify(service, times(1)).getRepositories("JonSmith");
    }

    private void verifyGetResult(List<RepositoryDto> dtoList) {
        val firstDto = dtoList.get(0);
        val firstBranch = firstDto.branches().get(0);
        assertEquals("cool-frontend-app", firstDto.name());
        assertEquals("Jonny123", firstDto.owner().login());
        assertEquals("master", firstBranch.name());
        assertEquals("8055c7a6264b3acd9c0c812a23026d6175acced4", firstBranch.latestCommit());
    }

    public Flux<RepositoryDto> getTestRepositories() {
        return Flux.just(RepositoryDto.builder()
                        .owner(OwnerDto.builder()
                                .login("Jonny123")
                                .build())
                        .name("cool-frontend-app")
                        .branches(List.of(BranchDto.builder()
                                        .name("master")
                                        .latestCommit("8055c7a6264b3acd9c0c812a23026d6175acced4")
                                        .build(),
                                BranchDto.builder()
                                        .name("dev")
                                        .latestCommit("2da98e0199361b9cb5ae6a14d7298b2eabdaaad2")
                                        .build()))
                        .build(),
                RepositoryDto.builder()
                        .owner(OwnerDto.builder()
                                .login("Jonny123")
                                .build())
                        .name("cool-backend-app")
                        .branches(List.of(BranchDto.builder()
                                .name("master")
                                .latestCommit("16bb979361e9b3563d2b0cdc861b97add092615f")
                                .build()))
                        .build());
    }
}
