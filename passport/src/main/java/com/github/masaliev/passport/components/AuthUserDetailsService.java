package com.github.masaliev.passport.components;

import com.github.masaliev.passport.domain.User;
import com.github.masaliev.passport.service.UserService;
import com.github.masaliev.shared.AuthUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public AuthUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username '" + username + "' not found");
        }

        return new AuthUser(user.getId(), user.getUsername(), user.getPassword(), user.isActive(), user.getRoles());
    }

}
