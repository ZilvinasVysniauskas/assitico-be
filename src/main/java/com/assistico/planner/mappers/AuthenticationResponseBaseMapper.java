package com.assistico.planner.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(source = "username", target = "username")
@Mapping(target = "expiresAt", expression = "java(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))")
@Mapping(target = "authenticationToken", expression = "java(jwtProvider.generateTokenWithUserName(request.getUsername()))")
public @interface AuthenticationResponseBaseMapper {
}
