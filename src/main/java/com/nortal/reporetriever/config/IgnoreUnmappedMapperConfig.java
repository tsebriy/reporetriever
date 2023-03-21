package com.nortal.reporetriever.config;

import org.mapstruct.MapperConfig;

import static org.mapstruct.ReportingPolicy.IGNORE;

@MapperConfig(unmappedTargetPolicy = IGNORE)
public interface IgnoreUnmappedMapperConfig {
}