package com.nortal.reporetriever.controller;

import com.nortal.reporetriever.model.dto.RepositoryDto;
import com.nortal.reporetriever.service.GitHubRepositoryService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("repository")
public class RepositoryController {

    private final GitHubRepositoryService service;

    @GetMapping
    public Flux<RepositoryDto> read(@Length(min = 1) @RequestParam String username) {
        return service.getRepositories(username);
    }
}
