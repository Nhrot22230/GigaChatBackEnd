package com.gigachat.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.gigachat.api", "com.gigachat.service"])
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}

