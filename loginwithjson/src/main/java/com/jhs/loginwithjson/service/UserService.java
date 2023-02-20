package com.jhs.loginwithjson.service;

import com.jhs.loginwithjson.controller.SignUpRequest;
import com.jhs.loginwithjson.auth.model.CustomUser;
import com.jhs.loginwithjson.domain.User;
import com.jhs.loginwithjson.repository.UserRepository;
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
        request.encryptPassword(passwordEncoder);
        User user = User.of(request);
        userRepository.save(user);
    }
}
