package com.example.demo.contoller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.github.GitHubService;
import com.example.demo.service.github.model.GitHubUser;

@RestController
public class GitHubServiceController {

    private final GitHubService gitHubService;

    public GitHubServiceController(@Qualifier("GitHubServiceImpl") GitHubService gitHubService) {
      this.gitHubService = gitHubService;
    }

    @GetMapping("/github/{username}")
    @ResponseBody
    public GitHubUser getGitHubUser(@PathVariable String username) throws Exception {
        return gitHubService.getGitHubUser(username);
    }

}