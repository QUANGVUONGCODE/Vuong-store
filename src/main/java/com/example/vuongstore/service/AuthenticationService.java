package com.example.vuongstore.service;

import com.example.vuongstore.dto.request.requestService.AuthenticationRequest;
import com.example.vuongstore.exception.AppException;
import com.example.vuongstore.exception.ErrorCode;
import com.example.vuongstore.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
     UserRepository userRepository;
     PasswordEncoder passwordEncoder;
     public boolean authenticate(AuthenticationRequest authenticationRequest){
         var user = userRepository.findByPhoneNumber(authenticationRequest.getPhoneNumber()).orElseThrow(
                 () -> new AppException(ErrorCode.USER_NOT_EXISTS)
         );

         return passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

     }
}
