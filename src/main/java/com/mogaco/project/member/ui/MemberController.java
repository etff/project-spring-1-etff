package com.mogaco.project.member.ui;

import com.mogaco.project.member.application.MemberService;
import com.mogaco.project.member.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<Long> registerMember(@Valid @RequestBody MemberRegisterDto dto) {
        final Long memberId = memberService.registerMember(dto);
        return ResponseEntity.created(URI.create("/members/" + memberId)).body(memberId);
    }
}
