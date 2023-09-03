package com.example.githubnotforkrepositories.services;

import com.example.githubnotforkrepositories.exceptions.ModelNotFoundException;
import com.example.githubnotforkrepositories.models.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BranchService {

    private final RestTemplate restTemplate;

    @Autowired
    public BranchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Branch> getBranches(String userName, String repositoryName) {
        try {
            Branch[] branches = restTemplate.getForObject(
                    "https://api.github.com/repos/{username}/{repoName}/branches",
                    Branch[].class,
                    userName,
                    repositoryName
            );

            if (branches != null) {
                return Arrays.asList(branches);
            } else {
                return Collections.emptyList();
            }
        } catch (HttpClientErrorException.NotFound exception) {
            throw new ModelNotFoundException("User or repository not found");
        }
    }
}
