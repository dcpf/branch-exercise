package com.example.demo.service.github.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitHubUser {
	@JsonProperty("user_name")
	private String userName;
	@JsonProperty("display_name")
	private String displayName;
	@JsonProperty("avatar")
	private String avatarUrl;
	@JsonProperty("geo_location")
	private String geoLocation;
	private String email;
	private String url;
	@JsonProperty("created_at")
	private String createdAt;
	private List<GitHubUserRepo> repos;

	public String getUserName() {
		return userName;
	}

	public GitHubUser setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getDisplayName() {
		return displayName;
	}

	public GitHubUser setDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public GitHubUser setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
		return this;
	}

	public String getGeoLocation() {
		return geoLocation;
	}

	public GitHubUser setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public GitHubUser setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getUrl() {
		return this.url;
	}

	public GitHubUser setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getCreatedAt() {
		return this.createdAt;
	}

	public GitHubUser setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public List<GitHubUserRepo> getRepos() {
		if (repos == null) {
			return new ArrayList<>();
		}
		return repos;
	}

	public GitHubUser setRepos(List<GitHubUserRepo> repos) {
		this.repos = repos;
		return this;
	}
}
