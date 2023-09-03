package com.example.githubnotforkrepositories.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Branch {

    private String name;

    private Commit commit;
}
