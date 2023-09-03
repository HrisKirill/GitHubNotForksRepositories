package com.example.githubnotforkrepositories.services;

import com.example.githubnotforkrepositories.exceptions.ModelNotFoundException;
import com.example.githubnotforkrepositories.models.GitHubRepository;
import com.example.githubnotforkrepositories.models.Owner;
import com.example.githubnotforkrepositories.services.constants.URLServiceConstants;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class GitHubRepositoryServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitHubRepositoryService repositoryService;

    private static final String USERNAME = "userName";

    private GitHubRepository[] getRepositories() {
        return new GitHubRepository[]{
                new GitHubRepository("gitHub", false, new Owner()),
                new GitHubRepository("Half", true, new Owner()),
                new GitHubRepository("Generation", false, new Owner())
        };
    }

    @Test
    public void getUserRepositoriesTest() {
        when(restTemplate.getForObject(
                URLServiceConstants.REPOSITORY_GET_REPOSITORIES_URL,
                GitHubRepository[].class,
                USERNAME)
        ).thenReturn(getRepositories());

        List<GitHubRepository> gitHubRepositoryList = repositoryService.getUserRepositories(USERNAME);

        assertNotNull(gitHubRepositoryList);
        assertTrue(gitHubRepositoryList.containsAll(List.of(getRepositories())));
    }

    @Test
    public void getEmptyBranchesTest() {
        when(restTemplate.getForObject(
                URLServiceConstants.REPOSITORY_GET_REPOSITORIES_URL,
                GitHubRepository[].class,
                USERNAME)
        ).thenReturn(null);

        assertEquals(Collections.emptyList(), repositoryService.getUserRepositories(USERNAME));
    }


    @Test
    public void getBranchesException() {
        when(restTemplate.getForObject(
                URLServiceConstants.REPOSITORY_GET_REPOSITORIES_URL,
                GitHubRepository[].class,
                USERNAME)
        ).thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(ModelNotFoundException.class, () -> repositoryService.getUserRepositories(USERNAME));
    }


    @Test
    public void getUserNotForkRepositoriesTest(){
        assertEquals(2,
                repositoryService.getUserNotForkRepositories(List.of(getRepositories())).size());
    }

}