package com.sberbank.may.security.config;

import com.sberbank.may.exception.NotFoundException;
import com.sberbank.may.user.model.User;
import com.sberbank.may.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findUserByPhone(name).orElseThrow(
                    () -> new NotFoundException(
                            "Пользователь с phone " +  name + " не найден"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getPhone())
                .password(user.getPassword())
                .authorities(user.getRole().getAuthority())
                .build();
    }
}
