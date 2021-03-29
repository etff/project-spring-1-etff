package com.mogaco.project.member.application;

import com.mogaco.project.global.utils.CustomPasswordEncoder;
import com.mogaco.project.member.domain.Member;
import com.mogaco.project.member.domain.MemberRepository;
import com.mogaco.project.member.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CustomPasswordEncoder passwordEncoder;

    public Long registerMember(MemberRegisterDto dto) {
        final String email = dto.getEmail();
        if (memberRepository.existsByEmail(email)) {
            throw new EmailDuplicationException(email);
        }
        final String encodedPassword = passwordEncoder.getEncodedPassword(dto);
        final Member member = Member.of(dto.getName(), dto.getEmail(), encodedPassword);
        final Member saved = memberRepository.save(member);

        return saved.getId();
    }
}
