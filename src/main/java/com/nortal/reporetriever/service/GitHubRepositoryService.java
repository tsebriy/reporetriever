package com.nortal.reporetriever.service;

import com.nortal.reporetriever.exceptions.DataNotAvailableException;
import com.nortal.reporetriever.model.dto.BranchDto;
import com.nortal.reporetriever.model.dto.RepositoryDto;
import com.nortal.reporetriever.model.entity.Branch;
import com.nortal.reporetriever.model.entity.Commit;
import com.nortal.reporetriever.model.entity.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;
import static java.util.function.Predicate.isEqual;
import static java.util.function.Predicate.not;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.util.StringUtils.replace;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubRepositoryService {

    private final RepositoryMapper mapper;

    private final WebClient client;

    public Flux<RepositoryDto> getRepositories(String username) {
        return client.get().uri(format("users/%s/repos", username))
                .retrieve()
                .onStatus(isEqual(NOT_FOUND), resp -> Mono.error(new DataNotAvailableException()))
                .bodyToFlux(Repository.class)
                .filter(not(Repository::fork))
                .flatMap(rep -> mapper.repositoryEntityToDto(rep, this));
    }

    Flux<BranchDto> getBranchesForARepository(Repository repo) {
        return client.get()
                .uri(replace(repo.branches_url(), "{/branch}", ""))
                .retrieve()
                .bodyToFlux(Branch.class)
                .flatMap(branch -> mapper.branchAndRepositoryToBranchDto(branch, repo, this));
    }

    Mono<String> getLatestCommitForABranch(Branch branch, Repository repo) {
        return client.get()
                .uri(replace(repo.commits_url(), "{/sha}", "/" + branch.name()))
                .retrieve()
                .bodyToMono(Commit.class)
                .map(Commit::sha);
    }
}
