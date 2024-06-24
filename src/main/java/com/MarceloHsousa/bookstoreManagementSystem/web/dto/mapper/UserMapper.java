package com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper;

import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.ui.Model;

public class UserMapper {

    public static User toUser(UserCreateDto userCreateDto){

        return  new ModelMapper().map(userCreateDto, User.class);
    }

    public static UserResponseDto toDto(User user){


        String role = user.getRole().name();
        PropertyMap<User, UserResponseDto> props = new PropertyMap<User, UserResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(user, UserResponseDto.class);

    }
}
