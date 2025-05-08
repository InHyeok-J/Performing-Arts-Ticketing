package com.performance.web.api.member.domain

class Member(
    id: Long = 0L,
    name: String,
) {

    private val _id = id
    private val _name = name

    fun getId() = _id

    fun getName() = _name
}
