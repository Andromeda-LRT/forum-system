package com.forumsystem.services.contracts;

import com.forumsystem.modelhelpers.UserModelFilterOptions;
import com.forumsystem.models.Post;
import com.forumsystem.models.User;

import java.util.List;

public interface UserService {

    List<User> getAll(User user, UserModelFilterOptions userFilter);

    User get(int id, User user);

    User getUserByUsername(String username);

    void create(User user);

    User update(User userToUpdate, User loggedUser);

    void delete(int id, User user);

    List<Post> getUserPosts(String username);

    void blockUser(int id, User user);

    void unblockUser(int id, User user);

    long getCountUsers();

    void giveUserAdminRights(User user, User loggedUser);

    public void checkPermissions(User userToUpdate, User loggedUser);

    boolean checkIfAdmin(User user);

    boolean checkIfAdminBoolean(User loggedUser);
}
