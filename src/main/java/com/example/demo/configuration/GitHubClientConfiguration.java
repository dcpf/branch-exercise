package com.example.demo.configuration;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitHubClientConfiguration {
  
  @Bean
	GitHub gitHubClient() throws Exception {
		return new GitHubBuilder().build();
	}
}
