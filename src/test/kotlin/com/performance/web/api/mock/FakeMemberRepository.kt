package com.performance.web.api.mock

import com.performance.web.api.member.domain.Member
import com.performance.web.api.member.domain.MemberRepository
import java.util.*

class FakeMemberRepository : MemberRepository {

    private var autoIncrementId = 1L
    private var store = mutableMapOf<Long, Member>()

    override fun findById(id: Long): Optional<Member> {
        return Optional.ofNullable(store[id])
    }

    override fun save(member: Member): Member {
        val newMember =
            Member(
                id = if (member.getId() == 0L) autoIncrementId++ else member.getId(),
                name = member.getName(),
                email = member.getEmail()
            )
        store.put(newMember.getId(), newMember)
        return newMember
    }
}
