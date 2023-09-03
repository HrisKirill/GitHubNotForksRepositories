package com.example.githubnotforkrepositories.controllers;

import com.example.githubnotforkrepositories.dto.GitHubRepositoryDTO;
import com.example.githubnotforkrepositories.mappers.MapModelToDTO;
import com.example.githubnotforkrepositories.models.GitHubRepository;
import com.example.githubnotforkrepositories.models.Owner;
import com.example.githubnotforkrepositories.services.BranchService;
import com.example.githubnotforkrepositories.services.GitHubRepositoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class GitHubControllerTest {

    @Mock
    GitHubRepositoryService repositoryService;

    @Mock
    BranchService branchService;

    @Mock
    MapModelToDTO mapModelToDTO;

    @InjectMocks
    GitHubController gitHubController;

    @Autowired
    MockMvc mockMvc;

    private  static final String USERNAME = "userName";
    private  static final String REP_NAME = "repName";
    private static final String  GET_REPOSITORIES_URL = "/github/api/repositories/userName";

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(gitHubController)
                .build();
    }


    private List<GitHubRepository> getTestReps() {
        return List.of(new GitHubRepository("gitHub", false, new Owner()));
    }

    @Test
    public void getRepositoriesTest() throws Exception {
        given(repositoryService.getUserRepositories(USERNAME)).willReturn(getTestReps());
        given(branchService.getBranches(USERNAME, REP_NAME))
                .willReturn(Collections.emptyList());

        given(repositoryService.getUserNotForkRepositories(getTestReps()))
                .willReturn(getTestReps());

        mockMvc.perform(get(GET_REPOSITORIES_URL)
                        .header("Accept", MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getRepositoriesWithXMLHeaderTest() throws Exception {
        given(repositoryService.getUserRepositories(USERNAME)).willReturn(getTestReps());
        given(branchService.getBranches(USERNAME, REP_NAME))
                .willReturn(Collections.emptyList());

        mockMvc.perform(get(GET_REPOSITORIES_URL)
                        .header("Accept", MediaType.APPLICATION_XML_VALUE))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void repositoriesIsEmptyTest() throws Exception {
        given(repositoryService.getUserRepositories(USERNAME)).willReturn(Collections.emptyList());
        given(branchService.getBranches(USERNAME, REP_NAME))
                .willReturn(Collections.emptyList());

        mockMvc.perform(get(GET_REPOSITORIES_URL)
                        .header("Accept", MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}