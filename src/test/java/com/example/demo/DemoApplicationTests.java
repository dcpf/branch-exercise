package com.example.demo;

import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GitHub gitHubClient;

	@Test
	void testGetGitHubUser() throws Exception {

		GHRepository repo1 = mockGHRepository("repo1", "http://repo1.com");
		GHRepository repo2 = mockGHRepository("repo2", "http://repo2.com");

		var repos = new HashMap<String, GHRepository>();
		repos.put("1", repo1);
		repos.put("2", repo2);

		GHUser ghUser = mockGHUser();
		doReturn("avatarUrl").when(ghUser).getAvatarUrl();
		doReturn(getDate()).when(ghUser).getCreatedAt();
		doReturn("email").when(ghUser).getEmail();
		doReturn("location").when(ghUser).getLocation();
		doReturn("name").when(ghUser).getName();
		doReturn(URI.create("http://example.com").toURL()).when(ghUser).getUrl();
		doReturn(repos).when(ghUser).getRepositories();
		doReturn(ghUser).when(gitHubClient).getUser("test1");

		String resp = doGetSuccess("/github/test1");
		assertEquals(
				"{\"email\":\"email\",\"url\":\"http://example.com\",\"repos\":[{\"name\":\"repo1\",\"url\":\"http://repo1.com\"},{\"name\":\"repo2\",\"url\":\"http://repo2.com\"}],\"user_name\":\"test1\",\"display_name\":\"name\",\"avatar\":\"avatarUrl\",\"geo_location\":\"location\",\"created_at\":\"1999-01-10 06:02:03\"}",
				resp);
	}

	@Test
	void testGetGitHubUserWithAllNulls() throws Exception {

		GHUser ghUser = mockGHUser();
		doReturn(ghUser).when(gitHubClient).getUser("test2");

		String resp = doGetSuccess("/github/test2");
		assertEquals("{\"email\":null,\"url\":null,\"repos\":[],\"user_name\":\"test2\",\"display_name\":null,\"avatar\":null,\"geo_location\":null,\"created_at\":null}", resp);

	}

	@Test
	void testGetGitHubUserWithNoRepos() throws Exception {

		GHUser ghUser = mockGHUser();
		doReturn(null).when(ghUser).getRepositories();
		doReturn(ghUser).when(gitHubClient).getUser("test3");

		String resp = doGetSuccess("/github/test3");
		assertEquals("{\"email\":null,\"url\":null,\"repos\":[],\"user_name\":\"test3\",\"display_name\":null,\"avatar\":null,\"geo_location\":null,\"created_at\":null}", resp);

	}

	@Test
	void testGetGitHubUserThrowsException() throws Exception {
		doThrow(new RuntimeException("Kaboom!")).when(gitHubClient).getUser("test4");
		String resp = doGetIsNotFoundError("/github/test4");
		assertEquals("{\"error\":\"Kaboom!\"}", resp);
	}

	@Test
	void testGetGitHubRepositoriesThrowsException() throws Exception {
		GHUser ghUser = mockGHUser();
		doReturn(ghUser).when(gitHubClient).getUser("test5");
		doThrow(new RuntimeException("Kaboom!")).when(ghUser).getRepositories();
		String resp = doGetIsNotFoundError("/github/test5");
		assertEquals("{\"error\":\"Kaboom!\"}", resp);
	}

	//
	// helper methods
	//

	private String doGetSuccess(String url) throws Exception {
		return mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
	}

	private String doGetIsNotFoundError(String url) throws Exception {
		return mockMvc.perform(get(url)).andDo(print()).andExpect(status().isNotFound()).andReturn().getResponse()
				.getContentAsString();
	}

	private GHUser mockGHUser() {
		return mock(CustomGHUser.class);
	}

	private GHRepository mockGHRepository(String name, String url) throws Exception {
		var repo = mock(CustomGHRepository.class);
		doReturn(name).when(repo).getName();
		doReturn(URI.create(url).toURL()).when(repo).getUrl();
		return repo;
	}

	private Date getDate() {
		var ldt = LocalDateTime.of(1999, 1, 10, 1, 2, 3);
		return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}

	// Mocking ghUser.getUrl() and ghRepo.getUrl() randomly fail with
	// WrongTypeOfReturnValue exception for some unknown reason.
	// These custom classes are a work-around.

	class CustomGHUser extends GHUser {
		@Override
		public URL getUrl() {
			return null;
		}
	}

	class CustomGHRepository extends GHRepository {
		@Override
		public URL getUrl() {
			return null;
		}
	}
}
