package com.example.vuongstore.controller;

import com.example.vuongstore.dto.request.requestService.AuthenticationRequest;
import com.example.vuongstore.dto.request.requestService.IntrospectRequest;
import com.example.vuongstore.dto.request.requestService.LogoutRequest;
import com.example.vuongstore.dto.request.requestService.RefeshRequest;
import com.example.vuongstore.dto.response.ApiResponse;
import com.example.vuongstore.dto.response.responseService.AuthenticationResponse;
import com.example.vuongstore.dto.response.responseService.IntrospectResponse;
import com.example.vuongstore.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }


    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefeshRequest request) throws ParseException, JOSEException{
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<String> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException{
        authenticationService.logout(request);
        return ApiResponse.<String>builder()
                .result("logout successfully")
                .build();
    }
}
