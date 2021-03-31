package com.mogaco.project.member.infra;

import com.mogaco.project.member.domain.Member;
import com.mogaco.project.member.domain.MemberRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JpaMemberRepository extends CrudRepository<Member, Long>, MemberRepository {
    Member save(Member member);

    boolean existsByEmail(String email);

    Optional<Member> findById(Long id);

    Optional<Member> findByIdAndDeletedIsFalse(Long id);

    Optional<Member> findByEmail(String email);
}
