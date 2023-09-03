package com.example.githubnotforkrepositories.services.constants;

public final class URLServiceConstants {

    public static final String REPOSITORY_GET_REPOSITORIES_URL = "https://api.github.com/users/{userName}/repos";
    public static final String BRANCH_GET_BRANCHES_URL = "https://api.github.com/repos/{username}/{repoName}/branches";

    private URLServiceConstants() {
    }
}
