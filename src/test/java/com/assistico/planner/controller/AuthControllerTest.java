package com.assistico.planner.controller;

import com.assistico.planner.dto.api.AuthenticationResponse;
import com.assistico.planner.dto.request.RegistrationRequest;
import com.assistico.planner.service.auth.AuthService;
import com.assistico.planner.service.auth.RefreshTokenService;
import com.assistico.planner.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @MockBean
    private RefreshTokenService refreshTokenService;
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String createInvalidRegistrationRequest(RegistrationRequestFields replacementField, String replacementValue) throws JsonProcessingException {
        return objectMapper.writeValueAsString(RegistrationRequest.builder()
                .email(replacementField.equals(RegistrationRequestFields.EMAIL) ?
                        replacementValue : RegistrationRequestFieldsInput.VALID_EMAIL.input())
                .username(replacementField.equals(RegistrationRequestFields.USERNAME) ?
                        replacementValue : RegistrationRequestFieldsInput.VALID_USERNAME.input())
                .password(replacementField.equals(RegistrationRequestFields.PASSWORD) ?
                        replacementValue : RegistrationRequestFieldsInput.VALID_PASSWORD.input())
                .confirmPassword(replacementField.equals(RegistrationRequestFields.PASSWORD) ?
                        replacementValue : RegistrationRequestFieldsInput.VALID_PASSWORD.input())
                .build());
    }

    private String createValidRegistrationRequest() throws JsonProcessingException {
        return objectMapper.writeValueAsString(RegistrationRequest.builder()
                .confirmPassword(RegistrationRequestFieldsInput.VALID_PASSWORD.input())
                .password(RegistrationRequestFieldsInput.VALID_PASSWORD.input())
                .username(RegistrationRequestFieldsInput.VALID_USERNAME.input())
                .email(RegistrationRequestFieldsInput.VALID_EMAIL.input())
                .build());
    }

    private RequestBuilder requestBuilder(String requestBody) {
        return MockMvcRequestBuilders
                .post("/api/auth/register")
                .accept(MediaType.APPLICATION_JSON).content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("should successfully register user")
    void shouldRegisterUser_success() throws Exception {

        Mockito.when(this.authService.register(any(RegistrationRequest.class))).thenReturn(AuthenticationResponse.builder().build());

        RequestBuilder requestBuilder = requestBuilder(createValidRegistrationRequest());
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("should forbid request with username shorten than 3 characters")
    void shouldRegisterUser_fail_username_too_short() throws Exception {
        RequestBuilder requestBuilder = requestBuilder(createInvalidRegistrationRequest(
                RegistrationRequestFields.USERNAME, RegistrationRequestFieldsInput.TOO_SHORT_USERNAME.input()
        ));
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("should forbid request with username longer then 20 characters")
    void shouldRegisterUser_fail_username_too_long() throws Exception {
        RequestBuilder requestBuilder = requestBuilder(createInvalidRegistrationRequest(
                RegistrationRequestFields.USERNAME, RegistrationRequestFieldsInput.TOO_LONG_USERNAME.input()
        ));
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should forbid request with blank username")
    void shouldRegisterUser_fail_username_is_blank() throws Exception {
        RequestBuilder requestBuilder = requestBuilder(createInvalidRegistrationRequest(
                RegistrationRequestFields.USERNAME, ""
        ));
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should forbid request with not unique username")
    void shouldRegisterUser_username_not_unique() throws Exception {
        Mockito.when(userService.isUserNameExisting(any(String.class))).thenReturn(true);
        RequestBuilder requestBuilder = requestBuilder(createValidRegistrationRequest());
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should forbid request with invalid email")
    void shouldRegisterUser_fail_email_is_invalid() throws Exception {
        RequestBuilder requestBuilder = requestBuilder(createInvalidRegistrationRequest(
                RegistrationRequestFields.EMAIL, RegistrationRequestFieldsInput.INVALID_EMAIL.input()
        ));
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should forbid request with blank email")
    void shouldRegisterUser_fail_email_is_blank() throws Exception {
        RequestBuilder requestBuilder = requestBuilder(createInvalidRegistrationRequest(
                RegistrationRequestFields.EMAIL, ""
        ));
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should forbid request with not unique email")
    void shouldRegisterUser_email_not_unique() throws Exception {
        Mockito.when(userService.isUserNameExisting(any(String.class))).thenReturn(true);
        RequestBuilder requestBuilder = requestBuilder(createValidRegistrationRequest());
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should forbid request with too short password")
    void shouldRegisterUser_fail_password_too_short() throws Exception {
        RequestBuilder requestBuilder = requestBuilder(createInvalidRegistrationRequest(
                RegistrationRequestFields.PASSWORD, RegistrationRequestFieldsInput.INVALID_PASSWORD_TOO_SHORT.input()
        ));
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should forbid request with too long password")
    void shouldRegisterUser_fail_password_too_long() throws Exception {
        RequestBuilder requestBuilder = requestBuilder(createInvalidRegistrationRequest(
                RegistrationRequestFields.PASSWORD, RegistrationRequestFieldsInput.INVALID_PASSWORD_TOO_LONG.input()
        ));
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should forbid request with password with no number")
    void shouldRegisterUser_fail_password_no_number() throws Exception {
        RequestBuilder requestBuilder = requestBuilder(createInvalidRegistrationRequest(
                RegistrationRequestFields.PASSWORD, RegistrationRequestFieldsInput.INVALID_PASSWORD_NO_NUMBER.input()
        ));
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should forbid request with password with no symbols")
    void shouldRegisterUser_fail_password_no_symbols() throws Exception {
        RequestBuilder requestBuilder = requestBuilder(createInvalidRegistrationRequest(
                RegistrationRequestFields.PASSWORD, RegistrationRequestFieldsInput.INVALID_PASSWORD_NO_SYMBOL.input()
        ));
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should forbid request with password with no capital_letter")
    void shouldRegisterUser_fail_password_no_capital_letter() throws Exception {
        RequestBuilder requestBuilder = requestBuilder(createInvalidRegistrationRequest(
                RegistrationRequestFields.PASSWORD, RegistrationRequestFieldsInput.INVALID_PASSWORD_NO_CAPITAL_LETTER.input()
        ));
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should forbid request with password not matching confirmation password")
    void shouldRegisterUser_fail_confirmation_password_not_matching() throws Exception {
        RequestBuilder requestBuilder = requestBuilder(
                objectMapper.writeValueAsString(RegistrationRequest.builder()
                                .email(RegistrationRequestFieldsInput.VALID_USERNAME.input())
                                .username(RegistrationRequestFieldsInput.VALID_USERNAME.input())
                                .password(RegistrationRequestFieldsInput.VALID_PASSWORD.input())
                                .confirmPassword(RegistrationRequestFieldsInput.NOT_MATCHING_CONFIRMATION_PASSWORD.input())
                        .build())
        );
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should successfully register user")
    void shouldActivateUser_success() throws Exception {

        Mockito.when(this.authService.register(any(RegistrationRequest.class))).thenReturn(AuthenticationResponse.builder().build());

        RequestBuilder requestBuilder = requestBuilder(createValidRegistrationRequest());
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }








}