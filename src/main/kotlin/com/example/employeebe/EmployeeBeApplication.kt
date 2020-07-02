package com.example.employeebe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoRepositories
@SpringBootApplication
class EmployeeBeApplication

fun main(args: Array<String>) {
    runApplication<EmployeeBeApplication>(*args)
}
