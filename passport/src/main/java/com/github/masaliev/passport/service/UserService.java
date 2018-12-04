package com.github.masaliev.passport.service;

import com.github.masaliev.passport.domain.User;
import com.github.masaliev.passport.domain.dto.SignUpRequest;
import com.github.masaliev.passport.exceptions.DuplicateUsernameException;
import com.github.masaliev.passport.repository.UserRepository;
import com.github.masaliev.shared.UserRole;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Add new user to database
     *
     * @param signUpRequest fields from sign up form
     * @return Just created user
     * @throws DuplicateUsernameException if username already used will throw
     * exception
     */
    public User addUser(SignUpRequest signUpRequest) throws DuplicateUsernameException {
        User userFromDb = userRepository.findByUsername(signUpRequest.getUsername());
        if (userFromDb != null) {
            throw new DuplicateUsernameException();
        }
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setActive(true); //@TODO send email to activate
        user.setRoles(Collections.singleton(UserRole.USER));
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);
        return user;
    }

}
