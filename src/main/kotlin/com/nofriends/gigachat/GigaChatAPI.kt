package com.nofriends.gigachat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.nofriends.gigachat.repository"])
class GigaChatAPI

fun main(args: Array<String>) {
	runApplication<GigaChatAPI>(*args)
}
