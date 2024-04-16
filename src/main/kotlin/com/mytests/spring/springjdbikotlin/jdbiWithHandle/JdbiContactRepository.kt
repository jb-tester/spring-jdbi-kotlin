package com.mytests.spring.springjdbikotlin.jdbiWithHandle

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.result.ResultIterable
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.function.Consumer

@Repository
@Transactional
class JdbiContactRepository(@param:Qualifier("jdbiContact") private val jdbi: Jdbi) : ContactRepository {
    override fun findAll(): List<Contact?> {
        return jdbi.withHandle<ResultIterable<Contact?>, RuntimeException> { handle: Handle ->
            handle.createQuery(
                SELECT_ALL_CONTACTS_QUERY
            )
                .mapTo(Contact::class.java)
        }.list()
    }

    override fun findById(id: Int?): Optional<Contact?> {
        val SELECT_CONTACT_BY_ID_QUERY = "SELECT * FROM contacts WHERE id = :id"
        return jdbi.withHandle<ResultIterable<Contact?>, RuntimeException> { handle: Handle ->
            handle.createQuery(SELECT_CONTACT_BY_ID_QUERY)
                .bind("id", id)
                .mapTo(Contact::class.java)
        }.findFirst()
    }

    override fun findByName(name: String?): List<Contact?> {
        return jdbi.withHandle<ResultIterable<Contact?>, RuntimeException> { handle: Handle ->
            handle.createQuery("SELECT * FROM contacts WHERE name = :name")
                .bind("name", name)
                .mapTo(Contact::class.java)
        }.list()
    }

    override fun findByEmails(): List<Contact?> {
        return jdbi.withHandle<ResultIterable<Contact?>, RuntimeException> { handle: Handle ->
            handle.createQuery("SELECT * FROM contacts WHERE email in (<mails>)")
                .bindList("mails", listOf("valentin@foo.com", "vasiliy@foo.com"))
                .mapTo(Contact::class.java)
        }.list()
    }

    override fun save(contact: Contact): Contact {
        jdbi.useHandle<RuntimeException> { handle: Handle ->
            handle.createUpdate("INSERT INTO contacts(id, name, email) VALUES (:id, :name, :email);")
                .bind("id", contact.id)
                .bind("name", contact.name)
                .bind("email", contact.email)
                .execute()
        }
        return contact
    }

    override fun saveAll(contacts: List<Contact>): List<Contact> {
        jdbi.useHandle<RuntimeException> { handle: Handle ->
            val preparedBatch =
                handle.prepareBatch("INSERT INTO contacts(id, name, email) VALUES (:id, :name, :email);")
            contacts.forEach(Consumer { contact: Contact ->
                preparedBatch
                    .bind("id", contact.id)
                    .bind("name", contact.name)
                    .bind("email", contact.email)
                    .add()
            })
            preparedBatch.execute()
        }
        return contacts
    }

    companion object {
        private const val SELECT_ALL_CONTACTS_QUERY = "SELECT * FROM contacts"
    }
}
