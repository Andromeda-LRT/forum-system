package com.forumsystem.repositories;

import com.forumsystem.models.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();

    User getById(int id);

    User create(User user);

    User update(User user);
}
