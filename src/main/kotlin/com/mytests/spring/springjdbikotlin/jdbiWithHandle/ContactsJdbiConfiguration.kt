package com.mytests.spring.springjdbikotlin.jdbiWithHandle

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.h2.H2DatabasePlugin
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import javax.sql.DataSource

@Configuration
class ContactsJdbiConfiguration {
    @Bean
    fun jdbiContact(dataSource: DataSource): Jdbi {
        val dataSourceProxy =
            TransactionAwareDataSourceProxy(dataSource)
        val jdbi = Jdbi.create(dataSourceProxy)
        jdbi.installPlugins()
        jdbi.installPlugin(H2DatabasePlugin())
        //jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.registerRowMapper(Contact::class.java, ConstructorMapper.of(Contact::class.java))

        return jdbi
    }
}