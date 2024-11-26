package com.example.demo.service.github;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.service.github.model.GitHubUserRepo;
import com.example.demo.service.cache.CacheService;
import com.example.demo.service.github.model.GitHubUser;

@Service
@Qualifier("GitHubServiceImpl")
public class GitHubServiceImpl implements GitHubService {

	private final GitHub gitHubClient;
	private final CacheService cache;
	private final Logger logger;

	public GitHubServiceImpl(GitHub gitHubClient, @Qualifier("HashMapCache") CacheService cache, Logger logger) {
		this.gitHubClient = gitHubClient;
		this.cache = cache;
		this.logger = logger;
	}

	@Override
	public GitHubUser getGitHubUser(String username) throws Exception {
		return getUser(username);
	}

	private GitHubUser getUser(String username) throws Exception {

		GitHubUser gitHubUser = (GitHubUser) cache.get(username);
		if (gitHubUser != null) {
			return gitHubUser;
		}

		logger.info("Fetching user " + username + " from GitHub");

		// Need to research how best to manage this
		gitHubClient.connectAnonymously();

		// Get the user
		GHUser ghUser = gitHubClient.getUser(username);
		gitHubUser = new GitHubUser()
				.setAvatarUrl(ghUser.getAvatarUrl())
				.setCreatedAt(formatDate(ghUser.getCreatedAt()))
				.setDisplayName(ghUser.getName())
				.setEmail(ghUser.getEmail())
				.setGeoLocation(ghUser.getLocation())
				.setUrl(getUrlAsString(ghUser.getUrl()))
				.setUserName(username);

		// Get the user's repos
		Map<String, GHRepository> repos = ghUser.getRepositories();
		if (repos != null) {
			gitHubUser
					.setRepos(
							repos.values().stream().map(repo -> new GitHubUserRepo(repo.getName(), getUrlAsString(repo.getUrl())))
									.toList());
		}

		cache.add(username, gitHubUser);
		return gitHubUser;

	}

	private String getUrlAsString(URL url) {
		return url == null ? null : url.toString();
	}

	private String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		LocalDateTime ldt = date.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime();
		return ldt.format(fmt);
	}
}
