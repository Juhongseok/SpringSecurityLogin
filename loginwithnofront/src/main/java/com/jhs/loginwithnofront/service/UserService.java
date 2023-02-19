package com.jhs.loginwithnofront.service;

import com.jhs.loginwithnofront.controller.SignUpRequest;
import com.jhs.loginwithnofront.domain.CustomUser;
import com.jhs.loginwithnofront.domain.User;
import com.jhs.loginwithnofront.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).get();
        return new CustomUser(user);
    }

    public void saveUSer(SignUpRequest request) {
        User user = request.toEntity(passwordEncoder);
        userRepository.save(user);
    }
}
