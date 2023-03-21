package com.nortal.reporetriever.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record RepositoryDto(String name, OwnerDto owner, List<BranchDto> branches) {

}