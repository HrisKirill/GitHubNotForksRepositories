package com.example.githubnotforkrepositories.services;

import com.example.githubnotforkrepositories.exceptions.ModelNotFoundException;
import com.example.githubnotforkrepositories.models.GitHubRepository;
import com.example.githubnotforkrepositories.services.constants.URLServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
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
    public GitHubRepositoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<GitHubRepository> getUserRepositories(String userName) {
        try {
            GitHubRepository[] gitHubRepositories = restTemplate.getForObject(
                    URLServiceConstants.REPOSITORY_GET_REPOSITORIES_URL,
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
