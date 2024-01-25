package com.forumsystem.services;

import com.forumsystem.models.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User get(int id);

    User getUserByUsername(String username);

    User create(User user);

    User update(User user);

    void delete(int id, User user);
}
