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
//        User user = userRepository.findUserByName(name).orElseThrow(
//                    () -> new NotFoundException(
//                            "Пользователь с именем " + name + " не найден"));

//                User user = new User(null, "admin", "admin", "admintest@mail.ru", "+79119756817",
//                        Role.ROLE_ADMIN);

//            User user = new User(null, "Учитель", "Пароль", "test@mail.ru", "+79995363450",
//                    Role.ROLE_TEACHER);

//        String login = user.getName();
//        String password = user.getPassword();

        return org.springframework.security.core.userdetails.User
                .withUsername("admin")
                .password("admin")
                .authorities("ROLE_ADMIN")
                //.authorities(user.getRole().getAuthority())
                .build();
    }
}
