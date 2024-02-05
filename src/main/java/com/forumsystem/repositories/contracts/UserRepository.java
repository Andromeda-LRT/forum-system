package com.forumsystem.repositories.contracts;

import com.forumsystem.modelhelpers.UserModelFilterOptions;
import com.forumsystem.models.Post;
import com.forumsystem.models.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll(UserModelFilterOptions userFilter);

    User get(int id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    void create(User user);

    User update(User user);

    void delete(int id);

    List<Post> getUserPosts(String username);

    void blockUser(int id, User user);

    void unblockUser(int id, User user);

    boolean checkIfAdmin(int id);

    long getCountUsers();
}
