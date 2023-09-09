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
        User user = userRepository.findUserByName(name).orElseThrow(
                    () -> new NotFoundException(
                            "Пользователь с именем " + name + " не найден"));

//                User user = new User(null, "admin", "admin", "admintest@mail.ru", "+79119756817",
//                        Role.ROLE_ADMIN);


        return org.springframework.security.core.userdetails.User
                .withUsername(user.getName())
                .password(user.getPassword())
                .authorities(user.getRole().getAuthority())
                .build();
    }
}
