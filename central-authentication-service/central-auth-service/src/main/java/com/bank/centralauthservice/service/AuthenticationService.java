package com.bank.centralauthservice.service;

import com.bank.centralauthservice.constants.ErrorConstants;
import com.bank.centralauthservice.entity.Privilege;
import com.bank.centralauthservice.entity.UserPrivilege;
import com.bank.centralauthservice.entity.Users;
import com.bank.centralauthservice.exception.ForbiddenAccessException;
import com.bank.centralauthservice.exception.UnAuthorizedException;
import com.bank.centralauthservice.model.AuthenticationRequest;
import com.bank.centralauthservice.model.AuthenticationResponse;
import com.bank.centralauthservice.repository.PrivilegeRepository;
import com.bank.centralauthservice.repository.UserPrivilegeRepository;
import com.bank.centralauthservice.repository.UserRepository;
import com.bank.centralauthservice.security.jwt.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PrivilegeRepository privilegeRepository;

    private final UserPrivilegeRepository userPrivilegeRepository;

    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, PrivilegeRepository privilegeRepository, UserPrivilegeRepository userPrivilegeRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.privilegeRepository = privilegeRepository;
        this.userPrivilegeRepository = userPrivilegeRepository;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            logger.error("Username or Password is Incorrect. Exception is: {}", badCredentialsException.getMessage());
            throw new BadCredentialsException(ErrorConstants.AUTH_OO3_DESCRIPTION);
        }

        Optional<Users> userOptional = userRepository.findByUsername(authenticationRequest.getUserName());

        if (userOptional.isPresent()) {
            logger.info("Fetched User Details from database:: {}", userOptional.get());

            boolean userHasPrivilege = validateUserPrivilege(authenticationRequest, userOptional.get());
            logger.info("User has privilege:: {}", userHasPrivilege);

            if (userHasPrivilege) {
                String jwt = jwtService.generateJwtToken(userOptional.get(), authenticationRequest);
                long expirationTime = jwtService.deriveExpiration(authenticationRequest);

                return AuthenticationResponse.builder()
                        .jwt(jwt)
                        .expirationTime(expirationTime)
                        .build();
            } else {
                logger.error("User does not possess the requested privilege. Please Contact Technical Support");
                throw new ForbiddenAccessException(ErrorConstants.AUTH_007, ErrorConstants.AUTH_OO7_DESCRIPTION);
            }
        } else {
            logger.error("User details not found in Database. Please Contact Technical Support");
            throw new UnAuthorizedException(ErrorConstants.AUTH_004, ErrorConstants.AUTH_OO4_DESCRIPTION);
        }
    }

    private boolean validateUserPrivilege(AuthenticationRequest authenticationRequest, Users users) throws Exception {
        Optional<Privilege> privilege = privilegeRepository.findByPrivilegeName(authenticationRequest.getAccessRequestedFor());

        if (privilege.isPresent()) {
            logger.info("Fetched Privilege Details from database:: {}", privilege.get());

            List<UserPrivilege> userPrivileges = userPrivilegeRepository.findByUserId(users.getUserId());

            if (!userPrivileges.isEmpty()) {
                logger.info("Fetched User Privilege Details from database:: {}", userPrivileges);

                return userPrivileges.stream()
                        .anyMatch(userPrivilege -> Objects.equals(userPrivilege.getPrivilegeId(), privilege.get().getPrivilegeId()));
            } else {
                logger.error("No Privilege is available for this user. Please Contact Technical Support");
                throw new ForbiddenAccessException(ErrorConstants.AUTH_005, ErrorConstants.AUTH_OO5_DESCRIPTION);
            }
        } else {
            logger.error("Requested Privilege is not available in Database. Please Contact Technical Support");
            throw new ForbiddenAccessException(ErrorConstants.AUTH_006, ErrorConstants.AUTH_OO6_DESCRIPTION);
        }
    }
}
