package com.example.githubnotforkrepositories.dto;

import lombok.Data;

@Data
public class BranchDTO {

    private String name;

    private String lastCommitSHA;
}
