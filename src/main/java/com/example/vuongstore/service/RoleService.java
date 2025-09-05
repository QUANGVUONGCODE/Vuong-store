package com.example.vuongstore.service;

import com.example.vuongstore.dto.request.RoleRequest;
import com.example.vuongstore.dto.response.RoleResponse;
import com.example.vuongstore.entity.Role;
import com.example.vuongstore.exception.AppException;
import com.example.vuongstore.exception.ErrorCode;
import com.example.vuongstore.mapper.RoleMapper;
import com.example.vuongstore.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest request){
        if(roleRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.ROLE_EXISTS);
        }
        Role role = roleMapper.maptoRole(request);
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

}
