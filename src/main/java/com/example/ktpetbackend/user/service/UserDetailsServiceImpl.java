package com.example.ktpetbackend.user.service;


import com.example.ktpetbackend.user.dto.RoleDto;
import com.example.ktpetbackend.user.entity.User;
import com.example.ktpetbackend.user.entity.UserDetailsImpl;
import com.example.ktpetbackend.global.exception.BadRequestException;
import com.example.ktpetbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


//    public UserDetails loadUserByUsername(String id) {
//        RoleDto roleDto = getUserRoleById(Long.valueOf(id));
//        return new UserDetailsImpl(roleDto);
//    }

    public UserDetails loadUserByUsername(String username) {
        RoleDto roleDto = getUserRoleById(username);
        return new UserDetailsImpl(roleDto);
    }

    public RoleDto getUserRoleById(String username) {
        User user;
        try {
            user = userRepository.findByUsername(username).get();
        }
        catch(NoSuchElementException e) {
            throw new BadRequestException("존재하지 않는 유저입니다.");
        }
        List<String> userRole = new ArrayList<>();
        userRole.add(user.getUserRole());
        return RoleDto.builder()
                .username(user.getUsername())
                .id(user.getId())
                .password(user.getPassword())
                .roles(userRole)
                .build();
    }

}