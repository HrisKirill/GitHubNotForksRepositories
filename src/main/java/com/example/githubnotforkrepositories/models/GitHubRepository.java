package com.example.githubnotforkrepositories.models;

import lombok.Data;

@Data
public class GitHubRepository {

    private String name;

    private boolean isFork;

    private Owner owner;
}
