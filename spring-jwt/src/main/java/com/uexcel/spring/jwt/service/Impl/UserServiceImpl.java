package com.uexcel.spring.jwt.service.Impl;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.uexcel.spring.jwt.repository.UserRepository;
import com.uexcel.spring.jwt.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {

        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {

                Optional<User> user = userRepository.findByEmail(username);
                if (user.isPresent()) {
                    return (UserDetails) user.get();
                }

                throw new UsernameNotFoundException("User not found");
            }
        };

    }

}
