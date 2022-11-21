package com.assistico.planner.mappers;

import com.assistico.planner.dto.api.AuthenticationResponse;
import com.assistico.planner.dto.request.LoginRequest;
import com.assistico.planner.dto.request.RefreshTokenRequest;
import com.assistico.planner.dto.request.RegistrationRequest;
import com.assistico.planner.model.User;
import com.assistico.planner.security.JwtProvider;
import com.assistico.planner.service.auth.RefreshTokenService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@Mapper(
        componentModel = "spring", imports = Instant.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public abstract class AuthenticationMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected JwtProvider jwtProvider;

    @Autowired
    protected RefreshTokenService refreshTokenService;


    @Mapping(target = "enabled", constant = "false")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(registrationRequest.getPassword()))")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "id", ignore = true)
    public  abstract User registrationRequestToUser(RegistrationRequest registrationRequest);

    @Mapping(target = "refreshToken", expression = "java(refreshTokenService.generateRefreshToken().getToken())")
    @AuthenticationResponseBaseMapper
    public abstract AuthenticationResponse loginRequestToAuthenticationResponse(LoginRequest request);

    @Mapping(target = "refreshToken", expression = "java(refreshTokenService.generateRefreshToken().getToken())")
    @AuthenticationResponseBaseMapper
    public abstract AuthenticationResponse registrationRequestToAuthenticationResponse(RegistrationRequest request);

    @Mapping(target = "refreshToken", source = "refreshToken")
    @AuthenticationResponseBaseMapper
    public abstract AuthenticationResponse refreshTokenToAuthenticationResponse(RefreshTokenRequest request);



}
