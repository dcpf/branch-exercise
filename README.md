# Branch Take-Home Exercise

I think everything here should be pretty straight-forward. Highlights:

* I'm using standard Spring and Spring Boot annotations: `SpringBootApplication`, `Configuration`, `RestController`, `GetMapping`, `PathVariable`, `ResponseBody`, `Service`, `ControllerAdvice`, etc.
* The impl for interacting with the GitHub APIs is in `GitHubServiceImpl`. I'm using the "GitHub API for Java" library for its simplicity (as opposed to apache HttpClient or similar).
* The use of `@Qualifier` is not really necessary, as there is currently only one of each service implementation. But it will come in handy should more implementations be added.

## Prerequisites for running the app locally:

* Clone this repo
* Install Java 21 or above
* Install maven (I'm using 3.9.9)

## Start the app with:

```
./mvnw spring-boot:run
```

Hit the endpoint at: http://localhost:8080/github/{username}

## Unit Tests

There is one suite of tests in `DemoApplicationTests` that test the entire flow from the main app down to the service and back. Tests for individual classes could be added as needed.

To run unit tests:

```
./mvnw test
```