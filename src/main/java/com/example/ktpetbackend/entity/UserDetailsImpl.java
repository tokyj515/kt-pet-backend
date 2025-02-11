package com.example.ktpetbackend.entity;

import com.example.ktpetbackend.dto.RoleDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private RoleDto roleDto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleDto.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    //    @Override
    //    public Collection<? extends GrantedAuthority> getAuthorities() {
    //        Collection<GrantedAuthority> authorities = new ArrayList<>();
    //        for(String role : role.split(","))
    //            authorities.add(new SimpleGrantedAuthority(role));
    //        return authorities;
    //    }

    @Override
    public String getPassword() {
        return roleDto.getPassword();
    }

    @Override
    public String getUsername() {
        return String.valueOf(roleDto.getId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}