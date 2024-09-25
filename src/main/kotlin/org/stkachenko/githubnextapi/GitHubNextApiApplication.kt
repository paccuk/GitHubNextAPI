package org.stkachenko.githubnextapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.retry.annotation.EnableRetry

@SpringBootApplication
@EnableRetry
class GitHubNextApiApplication

fun main(args: Array<String>) {
    runApplication<GitHubNextApiApplication>(*args)
}
