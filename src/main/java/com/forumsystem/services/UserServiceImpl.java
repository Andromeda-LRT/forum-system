package com.forumsystem.services;

import com.forumsystem.models.Post;
import com.forumsystem.models.User;
import com.forumsystem.repositories.UserRepository;
import com.forumsystem.еxceptions.DuplicateEntityException;
import com.forumsystem.еxceptions.EntityNotFoundException;
import com.forumsystem.еxceptions.UnauthorizedOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.forumsystem.modelhelpers.ModelConstantHelper.PERMISSIONS_ERROR;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository){
        this.repository = repository;
    }
    @Override
    public List<User> getAll(User user) {
        checkIfAdmin(user);
        return repository.getAll();
    }

    @Override
    public User get(int id, User user) {
        checkPermissions(repository.get(id), user);
        return repository.get(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return repository.getUserByUsername(username);
    }

    @Override
    public void create(User user) {
        boolean duplicateExists = true;
        try{
            repository.getUserByUsername(user.getUsername());
        }catch (EntityNotFoundException e){
            duplicateExists = false;
        }

        if (duplicateExists){
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }
        repository.create(user);
    }

    @Override
    public User update(User userToUpdate, User loggedUser) {
        checkUserIsBlocked(loggedUser);
        checkPermissions(userToUpdate, loggedUser);
        return repository.update(userToUpdate);
    }

    @Override
    public void delete(int id, User user) {
        checkPermissions(repository.get(id), user);
        repository.delete(id);

    }

    @Override
    public List<Post> getUserPosts(String username) {
        return repository.getUserPosts(username);
    }

    @Override
    public void blockUser(int id, User user) {
        checkIfAdmin(user);
        repository.blockUser(id, user);
    }

    @Override
    public void unblockUser(int id, User user) {
        checkIfAdmin(user);
        repository.unblockUser(id, user);
    }

    @Override
    public void getCountUsers(){
        repository.getCountUsers();
    }

    private void checkPermissions(User userToUpdate, User loggedUser) {
        if (!repository.checkIfAdmin(loggedUser.getUserId()) && userToUpdate.getUserId() != loggedUser.getUserId()) {
            throw new UnauthorizedOperationException(PERMISSIONS_ERROR);
        }
    }

    private void checkIfAdmin(User loggedUser) {
        if (!repository.checkIfAdmin(loggedUser.getUserId())) {
            throw new UnauthorizedOperationException(PERMISSIONS_ERROR);
        }
    }

    private void checkUserIsBlocked(User loggedUser) {
        if (loggedUser.isBlocked()) {
            throw new UnauthorizedOperationException(PERMISSIONS_ERROR);
        }
    }
}
