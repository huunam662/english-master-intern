package com.example.englishmaster_be.domain.auth.controller;

import com.example.englishmaster_be.common.annotation.DefaultMessage;
import com.example.englishmaster_be.domain.auth.dto.request.*;
import com.example.englishmaster_be.domain.auth.service.IAuthService;
import com.example.englishmaster_be.domain.auth.dto.response.UserAuthResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Tag(name = "Auth")
@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {


    IAuthService authService;


    @PostMapping("/login")
    @DefaultMessage("Login successfully")
    public UserAuthResponse login(@RequestBody UserLoginRequest loginDTO) {

        return authService.login(loginDTO);
    }

    @PostMapping("/register")
    @DefaultMessage("Check your email for confirm register")
    public void register(
            @Valid @RequestBody UserRegisterRequest userRegisterRequest
    ) {

        authService.registerUser(userRegisterRequest);
    }

    @GetMapping("/register/confirm")
    @DefaultMessage("Confirm register successfully")
    public void confirmRegister(@RequestParam("token") UUID sessionActiveCode) {

        authService.confirmRegister(sessionActiveCode);
    }


    @GetMapping("/mailer/otp")
    @DefaultMessage("Check email to get a OTP code")
    public void forgetPassword(@RequestParam("email") String email) {

        authService.forgotPassword(email);
    }


    @GetMapping("/verify/otp")
    @DefaultMessage("OTP code confirm successfully")
    public void verifyOtp(@RequestParam String otp) {

        authService.verifyOtp(otp);
    }

    @PostMapping("/change/password")
    @DefaultMessage("Save password successfully")
    public UserAuthResponse changePassword(@Valid @RequestBody UserChangePasswordRequest changePasswordRequest){

        return authService.changePassword(changePasswordRequest);
    }


    @PostMapping("/change/password/forgot")
    @DefaultMessage("Save password successfully")
    public UserAuthResponse changePasswordForgot(@Valid @RequestBody UserChangePwForgotRequest changePwForgotRequest) {

        return authService.changePasswordForgot(changePwForgotRequest);
    }



    @PostMapping("/refresh/token")
    @DefaultMessage("Refresh code successfully")
    public UserAuthResponse refreshToken(@RequestBody UserRefreshTokenRequest refreshTokenDTO) {

        return authService.refreshToken(refreshTokenDTO);
    }



    @PostMapping("/logout")
    @DefaultMessage("Logout successfully")
    public void logoutUser(@RequestBody UserLogoutRequest userLogoutDTO) {

        authService.logoutOf(userLogoutDTO);
    }


}
