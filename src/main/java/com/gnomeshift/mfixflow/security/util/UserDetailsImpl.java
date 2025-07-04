package com.gnomeshift.mfixflow.security.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gnomeshift.mfixflow.user.User;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    @Getter
    private final long id;

    @Email
    private final String email;

    @Getter
    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
        return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getUsername() {
        return email;
    }
}
