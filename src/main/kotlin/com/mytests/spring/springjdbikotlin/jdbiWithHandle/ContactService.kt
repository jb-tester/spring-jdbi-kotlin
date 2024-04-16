package com.mytests.spring.springjdbikotlin.jdbiWithHandle

import org.springframework.stereotype.Service
import java.util.List

@Service
class ContactService(private val repo: JdbiContactRepository) {
    fun insertSingle() {
        repo.save(Contact(1, "vanya", "ivan@foo.com"))
    }


    fun bulkInsert() {
        val contacts = List.of(
            Contact(2, "valya", "valentin@foo.com"),
            Contact(3, "vasya", "vasiliy@foo.com"),
            Contact(4, "tanya", "tatiana@foo.com"),
            Contact(5, "danya", "daniil@foo.com")
        )
        repo.saveAll(contacts)
    }

    fun findAll() {
        println("====== findAll() contacts: ")
        for (contact in repo.findAll()) {
            println(contact)
        }
    }

    fun findById() {
        println("====== findById(3) contacts: ")
        println(repo.findById(3).get())
    }

    fun findByName() {
        println("====== findByName('tanya') contacts: ")
        for (contact in repo.findByName("tanya")) {
            println(contact)
        }
    }

    fun findByEmails() {
        println("====== findByEmails() contacts: ")
        for (contact in repo.findByEmails()) {
            println(contact)
        }
    }
}
