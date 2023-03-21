package com.nortal.reporetriever.service;

import com.nortal.reporetriever.config.IgnoreUnmappedMapperConfig;
import com.nortal.reporetriever.model.dto.BranchDto;
import com.nortal.reporetriever.model.dto.RepositoryDto;
import com.nortal.reporetriever.model.entity.Branch;
import com.nortal.reporetriever.model.entity.Repository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import reactor.core.publisher.Mono;

import java.util.List;

@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface RepositoryMapper {

    @Mapping(target = "latestCommit", source = "sha")
    BranchDto branchToBranchDto(Branch branch, String sha);

    @Mapping(target = "branches", source = "branches")
    RepositoryDto repositoryEntityAndBranchesToDto(Repository repo, List<BranchDto> branches);

    default Mono<RepositoryDto> repositoryEntityToDto(Repository repo, @Context GitHubRepositoryService service) {
        return service.getBranchesForARepository(repo)
                .collectList()
                .map(branches -> repositoryEntityAndBranchesToDto(repo, branches));
    }

    default Mono<BranchDto> branchAndRepositoryToBranchDto(Branch branch, Repository repository, @Context GitHubRepositoryService service) {
        return service.getLatestCommitForABranch(branch, repository)
                .map(sha -> branchToBranchDto(branch, sha));
    }
}
