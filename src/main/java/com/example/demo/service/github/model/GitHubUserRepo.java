package com.example.demo.service.github.model;

public class GitHubUserRepo {
  private String name;
  private String url;

  public GitHubUserRepo() {
    // default no-arg constructor
  }

  public GitHubUserRepo(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public GitHubUserRepo setName(String name) {
    this.name = name;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public GitHubUserRepo setUrl(String url) {
    this.url = url;
    return this;
  }
}
