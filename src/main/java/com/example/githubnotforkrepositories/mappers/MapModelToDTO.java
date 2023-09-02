package com.example.githubnotforkrepositories.mappers;

import com.example.githubnotforkrepositories.dto.BranchDTO;
import com.example.githubnotforkrepositories.dto.GitHubRepositoryDTO;
import com.example.githubnotforkrepositories.models.Branch;
import com.example.githubnotforkrepositories.models.GitHubRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapModelToDTO {

    public GitHubRepositoryDTO mapToRepositoryInfo(GitHubRepository repo, List<Branch> branches) {
        GitHubRepositoryDTO gitHubRepositoryDTO = new GitHubRepositoryDTO();
        gitHubRepositoryDTO.setRepositoryName(repo.getName());
        gitHubRepositoryDTO.setOwnerLogin(repo.getOwner().getLogin());

        if (!branches.isEmpty()) {
            gitHubRepositoryDTO.setBranches(
                    branches.stream()
                            .map(this::mapToBranchInfo)
                            .collect(Collectors.toList())
            );
        }

        return gitHubRepositoryDTO;
    }

    private BranchDTO mapToBranchInfo(Branch branch) {
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setName(branch.getName());
        branchDTO.setLastCommitSHA(branch.getCommit().getSha());
        return branchDTO;
    }
}
