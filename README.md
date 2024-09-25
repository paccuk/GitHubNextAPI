# GitHub Next API

GitHub Next is a Spring Boot RESTful API written in Kotlin language for providing information about repositories of
specific GitHub user.
GitHub Next API uses https://developer.github.com/v3 API as a data source.


## Requirements

- Java 17
- Gradle 8.8


## Installation

- Clone the repository:

```shell
git clone https://github.com/paccuk/GitHubNextAPI.git && cd ./GitHubNextAPI
```

- Build and run the application:

```shell
./gradlew build
```

```shell
./gradlew bootRun 
```


## How to use?

- Call the API:

```shell
curl -L \
  -H "Accept: application/json" \
  http://localhost:8080/api/githubnext/users/<USERNAME>/repos
```
