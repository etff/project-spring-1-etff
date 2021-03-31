package com.mogaco.project.member.domain;

import java.util.Optional;

/**
 * 회원 정보 저장소.
 */
public interface MemberRepository {

    Member save(Member member);

    boolean existsByEmail(String email);

    Optional<Member> findById(Long id);

    Optional<Member> findByIdAndDeletedIsFalse(Long id);

    Optional<Member> findByEmail(String email);
}
