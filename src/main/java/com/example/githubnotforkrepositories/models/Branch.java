package com.example.githubnotforkrepositories.models;

import lombok.Data;

@Data
public class Branch {

    private String name;

    private Commit commit;
}
