package com.performance.web.api.member.service

import com.performance.web.api.member.domain.Member
import com.performance.web.api.member.domain.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    fun findById(id: Long): Member = memberRepository.findByIdThrown(id)
}
