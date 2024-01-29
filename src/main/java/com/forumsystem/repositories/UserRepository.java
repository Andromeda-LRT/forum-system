package com.forumsystem.repositories;

import com.forumsystem.models.Post;
import com.forumsystem.models.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();

    User get(int id);

    User getUserByUsername(String username);

    void create(User user);

    User update(User user);

    void delete(int id);

    List<Post> getUserPosts(String username);

    void blockUser(int id, User user);

    void unblockUser(int id, User user);

    boolean checkIfAdmin(int id);

    long getCountUsers();
}
