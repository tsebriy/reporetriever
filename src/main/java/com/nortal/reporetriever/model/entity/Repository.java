package com.nortal.reporetriever.model.entity;

public record Repository(String name, Owner owner, String branches_url, String commits_url, Boolean fork) {
}
