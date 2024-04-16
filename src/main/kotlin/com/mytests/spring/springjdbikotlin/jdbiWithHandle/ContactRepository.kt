package com.mytests.spring.springjdbikotlin.jdbiWithHandle

import java.util.*

internal interface ContactRepository {
    fun findAll(): List<Contact?>
    fun findById(id: Int?): Optional<Contact?>
    fun findByName(name: String?): List<Contact?>
    fun findByEmails(): List<Contact?>
    fun save(Contact: Contact): Contact
    fun saveAll(Contacts: List<Contact>): List<Contact>
}