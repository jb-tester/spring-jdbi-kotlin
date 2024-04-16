package com.mytests.spring.springjdbikotlin

import com.mytests.spring.springjdbikotlin.jdbiWithHandle.ContactService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringJdbiKotlinApplication {
    @Bean
    fun init(contactService: ContactService) = CommandLineRunner {

        contactService.bulkInsert()
        contactService.insertSingle()
        contactService.findAll()
        contactService.findByName()
        contactService.findById()
        contactService.findByEmails()
    }
}
fun main() {
    runApplication<SpringJdbiKotlinApplication>()
}