package com.example.demo.service.github;

import com.example.demo.service.github.model.GitHubUser;

public interface GitHubService {
	GitHubUser getGitHubUser(String username) throws Exception;
}
