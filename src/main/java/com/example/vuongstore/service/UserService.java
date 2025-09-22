package com.example.vuongstore.service;

import com.example.vuongstore.dto.request.UserRequest;
import com.example.vuongstore.dto.request.requestUpdate.UserRequestUpdate;
import com.example.vuongstore.dto.response.UserResponse;
import com.example.vuongstore.entity.Role;
import com.example.vuongstore.entity.User;
import com.example.vuongstore.enums.RolePlay;
import com.example.vuongstore.exception.AppException;
import com.example.vuongstore.exception.ErrorCode;
import com.example.vuongstore.mapper.UserMapper;
import com.example.vuongstore.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;


    public UserResponse createUser(UserRequest request){
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTS);
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTS);
        }
        if(request.getFacebookAccountId() == 0 && request.getGoogleAccountId() == 0){
            String password = request.getPassword();
            request.setPassword(password);
        }

        if(!request.getPassword().equals(request.getRetypePassword())){
            throw new AppException(ErrorCode.RETYPE_PASSWORD_WRONG);
        }

        User user = userMapper.mapToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role = new Role();
        role.setId(1L);
        role.setName(RolePlay.USER.name());
        user.setRole(role);
        user.setActive(true);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUser(){
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PreAuthorize("#id == authentication.principal.claims['userId'] || hasRole('ADMIN')")
    public UserResponse updateUser(UserRequestUpdate request, Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTS);
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTS);
        }

        userMapper.updateUser(request, user);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("#id == authentication.principal.claims['userId'] || hasRole('ADMIN')")
    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));
        user.setActive(false);
        userRepository.save(user);
    }


    public UserResponse getMyInfor(){
        var context = SecurityContextHolder.getContext();
        String phoneNumber = context.getAuthentication().getName();
        log.info("ROLE:" + context.getAuthentication().getAuthorities());
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new AppException(ErrorCode.USER_EXISTS)
        );
        return userMapper.toUserResponse(user);
    }
}
