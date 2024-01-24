package com.forumsystem.dtomappers;

import com.forumsystem.models.User;
import com.forumsystem.models.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final UserService userService;

    @Autowired
    public UserMapper(UserService userService) {
        this.userService = userService;
    }

    public User createUserFromDto(UserDto userDto){
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());

        return user;
    }
}