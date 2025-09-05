package com.example.vuongstore.mapper;

import com.example.vuongstore.dto.request.UserRequest;
import com.example.vuongstore.dto.request.requestUpdate.UserRequestUpdate;
import com.example.vuongstore.dto.response.UserResponse;
import com.example.vuongstore.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapToUser(UserRequest userRequest);
    UserResponse toUserResponse(User user);
    @BeanMapping(nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UserRequestUpdate userRequestUpdate, @MappingTarget User user);
}
