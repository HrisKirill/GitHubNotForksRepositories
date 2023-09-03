package com.example.githubnotforkrepositories.services;

import com.example.githubnotforkrepositories.exceptions.ModelNotFoundException;
import com.example.githubnotforkrepositories.models.Branch;
import com.example.githubnotforkrepositories.models.Commit;
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
class BranchServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BranchService branchService;

    private static final String USERNAME = "userName";
    private static final String REP_NAME = "repositoryName";

    private Branch[] getTestBranches() {
        return new Branch[]{
                new Branch("branch1", new Commit()),
                new Branch("branch2", new Commit())
        };
    }

    @Test
    public void getBranchesTest() {
        when(restTemplate.getForObject(
                URLServiceConstants.BRANCH_GET_BRANCHES_URL,
                Branch[].class,
                USERNAME,
                REP_NAME)
        ).thenReturn(getTestBranches());

        List<Branch> branchList = branchService.getBranches(USERNAME, REP_NAME);

        assertNotNull(branchList);
        assertTrue(branchList.containsAll(List.of(getTestBranches())));
    }

    @Test
    public void getEmptyBranchesTest() {
        when(restTemplate.getForObject(
                URLServiceConstants.BRANCH_GET_BRANCHES_URL,
                Branch[].class,
                USERNAME,
                REP_NAME)
        ).thenReturn(null);

        assertEquals(Collections.emptyList(), branchService.getBranches(USERNAME, REP_NAME));
    }

    @Test
    public void getBranchesException() {
        when(restTemplate.getForObject(
                URLServiceConstants.BRANCH_GET_BRANCHES_URL,
                Branch[].class,
                USERNAME,
                REP_NAME)
        ).thenThrow(HttpClientErrorException.NotFound.class);

        assertThrows(ModelNotFoundException.class, () -> branchService.getBranches(USERNAME, REP_NAME));
    }

}