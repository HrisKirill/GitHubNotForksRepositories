package com.example.githubnotforkrepositories.services;

import com.example.githubnotforkrepositories.exceptions.ModelNotFoundException;
import com.example.githubnotforkrepositories.models.GitHubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubRepositoryService {
    private final RestTemplate restTemplate;

    @Autowired
    public GitHubRepositoryService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public List<GitHubRepository> getUserRepositories(String userName) {
        try {
            GitHubRepository[] gitHubRepositories = restTemplate.getForObject(
                    "https://api.github.com/users/{userName}/repos",
                    GitHubRepository[].class,
                    userName
            );

            return (gitHubRepositories != null) ? Arrays.asList(gitHubRepositories)
                    : Collections.emptyList();

        } catch (HttpClientErrorException.NotFound exception) {
            throw new ModelNotFoundException("User not found");
        }
    }


    public List<GitHubRepository> getUserNotForkRepositories(List<GitHubRepository> gitHubRepositories) {
        return gitHubRepositories.stream()
                .filter(rep -> !rep.isFork())
                .collect(Collectors.toList());
    }

}
