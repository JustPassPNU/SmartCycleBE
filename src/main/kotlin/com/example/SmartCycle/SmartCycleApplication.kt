package com.example.SmartCycle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SmartCycleApplication {
	init {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true")
	}
}

fun main(args: Array<String>) {
	runApplication<SmartCycleApplication>(*args)
}
