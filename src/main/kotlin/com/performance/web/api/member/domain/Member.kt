package com.performance.web.api.member.domain

class Member(
    id: Long = 0L,
    name: String,
    email: String
) {

    private val _id = id
    private val _name = name
    private val _email = email

    fun getId() = _id

    fun getName() = _name

    fun getEmail() = _email
}
