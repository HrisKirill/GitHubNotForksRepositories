## GithubNotForkRepositories

#### Start app and go to the browser or postman with url:

GET: http://localhost:8080/github/api/repositories/{userName}

#### If username is real get response like:
```
[
    {
        "repositoryName": ${repName},
        "ownerLogin": ${login},
        "branches": [
            {
            "name": ${branchName},
            "lastCommitSHA": ${commit}
            }
        ]
    }
]
```
#### Otherwise, receive 404 response in such a format:

```
{
    "status": ${status}
    "message": ${message}
}
```

#### If given header “Accept: application/xml”, receive 406 response in such a format:

```
{
    "status": ${status}
    "message": ${message}
}
```

