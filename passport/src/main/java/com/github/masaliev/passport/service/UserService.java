package com.github.masaliev.passport.service;

import com.github.masaliev.passport.domain.User;
import com.github.masaliev.passport.domain.dto.SignUpRequest;
import com.github.masaliev.passport.exceptions.DuplicateUsernameException;
import com.github.masaliev.passport.repository.UserRepository;
import com.github.masaliev.shared.UserRole;
import java.util.Collections;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MailSender mailSender;

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
        user.setEmail(signUpRequest.getEmail());
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(UserRole.USER));
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);
        
        String message = String.format(
                "Hello, %s \n\n" + 
                        "Please, visit next link to activate your account: http://localhost:8082/auth/activate/%s", //@TODO make link configurable
                user.getUsername(), user.getActivationCode());
        mailSender.sendMail(user.getEmail(), "Activation code", message);
        
        return user;
    }

    public boolean activateUser(String code){
        
        User user = userRepository.findByActivationCode(code);
        if (user == null){
            return false;
        }
        
        user.setActive(true);
        user.setActivationCode(null);
        userRepository.save(user);
        
        return true;
    }
}
