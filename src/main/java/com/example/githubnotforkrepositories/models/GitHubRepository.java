package com.example.githubnotforkrepositories.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GitHubRepository {

    private String name;

    private boolean isFork;

    private Owner owner;
}
