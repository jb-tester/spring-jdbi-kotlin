package com.mytests.spring.springjdbikotlin.jdbiWithHandle


class Contact(var id: Int, var name: String, var email: String) {
    override fun toString(): String {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}'
    }
}
