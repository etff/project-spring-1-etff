package com.mogaco.project.member.ui;

import com.mogaco.project.member.application.MemberService;
import com.mogaco.project.member.dto.MemberRegisterDto;
import com.mogaco.project.member.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

/**
 * 회원에 대한 요청을 처리한다.
 */
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 주어진 회원 정보를 등록한다.
     *
     * @param dto 회원 등록 명세서
     * @return 회원 식별자
     */
    @PostMapping
    public ResponseEntity<Long> registerMember(@Valid @RequestBody MemberRegisterDto dto) {
        final Long memberId = memberService.registerMember(dto);
        return ResponseEntity.created(URI.create("/members/" + memberId)).body(memberId);
    }

    @PatchMapping("{id}")
    public ResponseEntity updateMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberUpdateDto dto) {
        memberService.updateMember(id, dto);
        return ResponseEntity.ok().build();
    }

}
