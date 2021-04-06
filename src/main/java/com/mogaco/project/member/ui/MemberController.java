package com.mogaco.project.member.ui;

import com.mogaco.project.auth.application.LoginNotFoundException;
import com.mogaco.project.global.utils.SecurityUtil;
import com.mogaco.project.member.application.MemberService;
import com.mogaco.project.member.dto.MemberRegisterDto;
import com.mogaco.project.member.dto.MemberResponse;
import com.mogaco.project.member.dto.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
  private final SecurityUtil securityUtil;

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

  /**
   * 주어진 식별자에 해당하는 회원을 수정한다.
   *
   * @param id  사용자 식별자
   * @param dto 사용자 수정 명세서
   */
  @PatchMapping("{id}")
  public ResponseEntity updateMember(
          @PathVariable Long id, @Valid @RequestBody MemberUpdateDto dto) {
    memberService.updateMember(id, dto);
    return ResponseEntity.ok().build();
  }

  /**
   * 주어진 식별자에 해당하는 회원을 리턴한다.
   */
  @GetMapping("{id}")
  public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
    final MemberResponse memberResponse = memberService.getMember(id);
    return ResponseEntity.ok().body(memberResponse);
  }

  /**
   * 로그인한 회원의 정보를 리턴한다.
   */
  @GetMapping("/me")
  public ResponseEntity<MemberResponse> getLoginMember() {
    final Long loginId = securityUtil.getCurrentMemberId()
            .orElseThrow(LoginNotFoundException::new);
    final MemberResponse memberResponse = memberService.getMember(loginId);
    return ResponseEntity.ok().body(memberResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<MemberResponse> deleteMember(@PathVariable Long id) {
    memberService.deleteMember(id);
    return ResponseEntity.noContent().build();
  }
}
