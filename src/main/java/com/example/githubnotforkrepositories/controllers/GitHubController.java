package com.example.githubnotforkrepositories.controllers;

import com.example.githubnotforkrepositories.dto.GitHubRepositoryDTO;
import com.example.githubnotforkrepositories.exceptions.UnsupportedMediaTypeException;
import com.example.githubnotforkrepositories.mappers.MapModelToDTO;
import com.example.githubnotforkrepositories.models.GitHubRepository;
import com.example.githubnotforkrepositories.services.BranchService;
import com.example.githubnotforkrepositories.services.GitHubRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("github/api")
public class GitHubController {

    private final GitHubRepositoryService gitHubRepositoryService;
    private final BranchService branchService;
    private final MapModelToDTO mapper;

    @Autowired
    public GitHubController(GitHubRepositoryService gitHubRepositoryService, BranchService branchService, MapModelToDTO mapper) {
        this.gitHubRepositoryService = gitHubRepositoryService;
        this.branchService = branchService;
        this.mapper = mapper;
    }

    @GetMapping(value = "/repositories/{userName}")
    public ResponseEntity<List<GitHubRepositoryDTO>> getRepositories(
            @PathVariable String userName, @RequestHeader(value = "Accept") String acceptHeader) {

        if(acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)){
            throw new UnsupportedMediaTypeException("XML is not supported. Use JSON.");
        }

        List<GitHubRepository> gitHubRepositories = gitHubRepositoryService.
                getUserRepositories(userName);

        if (gitHubRepositories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<GitHubRepositoryDTO> repositoryDTOS = gitHubRepositoryService.
                getUserNotForkRepositories(gitHubRepositories).stream()
                .map(rep -> mapper.mapToRepositoryInfo(
                        rep,
                        branchService.getBranches(
                                rep.getOwner().getLogin(),
                                rep.getName()
                        )
                ))
                .toList();


        return ResponseEntity.ok(repositoryDTOS);
    }
}
