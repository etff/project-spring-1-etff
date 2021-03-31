package com.mogaco.project.member.application;

import com.mogaco.project.global.utils.CustomPasswordEncoder;
import com.mogaco.project.member.domain.Member;
import com.mogaco.project.member.domain.MemberRepository;
import com.mogaco.project.member.dto.MemberRegisterDto;
import com.mogaco.project.member.dto.MemberResponse;
import com.mogaco.project.member.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 정보를 다룬다.
 */
@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CustomPasswordEncoder passwordEncoder;

    /**
     * 사용자를 등록하고, 등록된 회원 식별자를 리턴한다.
     *
     * @param dto 등록할 회원 정보
     * @return 회원 식별자
     */
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

    /**
     * 등록된 회원 정보를 갱신한다.
     *
     * @param memberId 등록된 회원 식별자
     * @param dto      갱신할 사용자 정보
     */
    @Transactional
    public void updateMember(Long memberId, MemberUpdateDto dto) {
        final Member member = findMember(memberId);
        member.updateMember(dto.getName(), dto.getEmail());
    }

    /**
     * 회원 식별자에 해당하는 회정 정보를 리턴한다.
     *
     * @param id 회원 식별자
     * @return 사용자 정보
     * @throws MemberNotFoundException 존재하지 않는 회원 예외
     */
    public MemberResponse getMember(Long id) throws MemberNotFoundException {
        final Member member = findMember(id);
        return new MemberResponse(member);
    }

    private Member findMember(Long memberId) {
        return memberRepository
                .findByIdAndDeletedIsFalse(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }
}
