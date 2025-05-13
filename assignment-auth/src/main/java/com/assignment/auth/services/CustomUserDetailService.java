package com.assignment.auth.services;

import com.assignment.auth.entities.UserEntity;
import com.assignment.auth.model.UserDetailCustom;
import com.assignment.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
        return new UserDetailCustom(user);
    }
}
