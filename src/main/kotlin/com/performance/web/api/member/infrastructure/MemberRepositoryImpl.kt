package com.performance.web.api.member.infrastructure

import com.performance.web.api.member.domain.Member
import com.performance.web.api.member.domain.MemberRepository
import com.performance.web.api.member.infrastructure.jpa.MemberEntity
import com.performance.web.api.member.infrastructure.jpa.MemberJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class MemberRepositoryImpl(
    private val memberJpaRepository: MemberJpaRepository,
) : MemberRepository {

    override fun findById(id: Long): Optional<Member> = memberJpaRepository.findById(id).map { it.toDomain() }

    override fun save(member: Member): Member = memberJpaRepository.save(MemberEntity.fromDomain(member)).toDomain()
}
