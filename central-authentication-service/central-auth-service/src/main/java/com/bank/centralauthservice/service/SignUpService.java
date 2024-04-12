package com.bank.centralauthservice.service;

import com.bank.centralauthservice.constants.ErrorConstants;
import com.bank.centralauthservice.entity.Users;
import com.bank.centralauthservice.exception.UserSignUpException;
import com.bank.centralauthservice.model.SignUpRequest;
import com.bank.centralauthservice.model.SignUpResponse;
import com.bank.centralauthservice.model.Status;
import com.bank.centralauthservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SignUpService {
    private static final Logger logger = LoggerFactory.getLogger(SignUpService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SignUpService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SignUpResponse registerUser(SignUpRequest signUpRequest) throws UserSignUpException {
        try {
            Users users = new Users();
            users.setUsername(signUpRequest.getUserName());
            users.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

            userRepository.save(users);
            logger.info("User Details has been saved into database.");

            return SignUpResponse.builder()
                    .status(Status.SUCCESS)
                    .createdOn(LocalDateTime.now())
                    .build();
        } catch (Exception exception) {
            logger.error("Exception occurred during User Registration. Exception is: {}", exception);

            throw new UserSignUpException(ErrorConstants.AUTH_009, ErrorConstants.AUTH_OO9_DESCRIPTION);
        }
    }
}
