package com.nortal.reporetriever.model.entity;

import lombok.Builder;

@Builder
public record Commit(String sha) {
}
