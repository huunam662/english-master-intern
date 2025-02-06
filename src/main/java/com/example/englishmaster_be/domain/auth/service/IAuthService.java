package com.example.englishmaster_be.domain.auth.service;

import com.example.englishmaster_be.domain.auth.dto.request.*;
import com.example.englishmaster_be.domain.auth.dto.response.UserAuthResponse;
import com.example.englishmaster_be.domain.auth.dto.response.UserConfirmTokenResponse;
import com.example.englishmaster_be.model.user.UserEntity;

import java.util.UUID;

public interface IAuthService {

    UserAuthResponse login(UserLoginRequest userLoginRequest);

    void registerUser(UserRegisterRequest userRegisterRequest);

    void confirmRegister(UUID sessionActiveCode);

    void forgotPassword(String email);

    void verifyOtp(String otp);

    UserAuthResponse changePassword(UserChangePasswordRequest changePasswordRequest);

    UserAuthResponse changePasswordForgot(UserChangePwForgotRequest changePasswordRequest);

    UserAuthResponse refreshToken(UserRefreshTokenRequest refreshTokenRequest);

    void logoutOf(UserLogoutRequest userLogoutRequest);

    UserConfirmTokenResponse createConfirmationToken(UserEntity user);

}
