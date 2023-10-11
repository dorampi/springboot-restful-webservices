package com.dxl.springboot.mapper;

import com.dxl.springboot.dto.UserDto;
import com.dxl.springboot.entity.User;

public class UserMapper {

    // convert USER JPA entity into UserDto
    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    // convert USER JPA entity into UserDto
    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail()
        );
    }
}
