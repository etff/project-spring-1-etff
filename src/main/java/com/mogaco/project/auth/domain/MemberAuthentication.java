package com.mogaco.project.auth.domain;


import com.mogaco.project.member.domain.Role;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 회원 인증 정보.
 */
public class MemberAuthentication extends AbstractAuthenticationToken {
    private final Long memberId;

    public MemberAuthentication(Long memberId, List<Role> roles) {
        super(authorities(roles));
        this.memberId = memberId;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return memberId;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public String toString() {
        return "Authentication(" + memberId + ")";
    }

    private static List<GrantedAuthority> authorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberAuthentication)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MemberAuthentication that = (MemberAuthentication) o;
        return memberId.equals(that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), memberId);
    }
}
