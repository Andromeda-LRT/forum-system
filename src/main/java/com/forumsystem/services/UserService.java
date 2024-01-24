package com.forumsystem.services;

import com.forumsystem.models.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User get(int id);

    void create(User user);

    void update(User user);

    void delete(int id, User user);
}
