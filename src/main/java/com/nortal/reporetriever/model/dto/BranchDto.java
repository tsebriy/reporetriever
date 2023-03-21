package com.nortal.reporetriever.model.dto;

import lombok.Builder;

@Builder
public record BranchDto(String name, String latestCommit) {
}
