package com.example.githubnotforkrepositories.dto;

import lombok.Data;

import java.util.List;

@Data
public class GitHubRepositoryDTO {

    private String repositoryName;

    private String ownerLogin;

    private List<BranchDTO> branches;
}
