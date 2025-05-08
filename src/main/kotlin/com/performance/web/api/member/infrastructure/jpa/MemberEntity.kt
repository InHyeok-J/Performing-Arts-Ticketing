package com.performance.web.api.member.infrastructure.jpa

import com.performance.web.api.member.domain.Member
import jakarta.persistence.*

@Entity
@Table(name = "member")
class MemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    var email: String,
) {

    fun toDomain(): Member =
        Member(
            id = id,
            name = name,
            email = email
        )

    companion object {
        fun fromDomain(customer: Member): MemberEntity =
            MemberEntity(
                id = customer.getId(),
                name = customer.getName(),
                email = customer.getEmail()
            )
    }
}
