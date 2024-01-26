package com.forumsystem.services;

import com.forumsystem.models.Post;
import com.forumsystem.models.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User get(int id);

    User getUserByUsername(String username);

    void create(User user);

    User update(User userToUpdate, User loggedUser);

    void delete(int id, User user);

    List<Post> getUserPosts(String username);

    void blockUser(int id, User user);

    void unblockUser(int id, User user);
}
