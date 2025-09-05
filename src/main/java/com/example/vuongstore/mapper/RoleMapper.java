package com.example.vuongstore.mapper;

import com.example.vuongstore.dto.request.RoleRequest;
import com.example.vuongstore.dto.response.RoleResponse;
import com.example.vuongstore.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role maptoRole(RoleRequest roleRequest);
    RoleResponse toRoleResponse(Role role);
    void updateRole(RoleRequest roleRequest, @MappingTarget Role role);
}
