package com.forumsystem.services;

import com.forumsystem.modelhelpers.UserModelFilterOptions;
import com.forumsystem.models.Post;
import com.forumsystem.models.User;
import com.forumsystem.repositories.contracts.UserRepository;
import com.forumsystem.services.contracts.UserService;
import com.forumsystem.еxceptions.DuplicateEntityException;
import com.forumsystem.еxceptions.EntityNotFoundException;
import com.forumsystem.еxceptions.UnauthorizedOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.forumsystem.modelhelpers.ModelConstantHelper.PERMISSIONS_ERROR;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository){
        this.repository = repository;
    }
    @Override
    public List<User> getAll(User user, UserModelFilterOptions userFilter) {
        checkIfAdmin(user);
        return repository.getAll(userFilter);
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
        boolean duplicateUserNameExists = true;
        boolean duplicateEmailNameExists = true;

        try{
            repository.getUserByUsername(user.getUsername());
        }catch (EntityNotFoundException e){
            duplicateUserNameExists = false;
        }
        if (duplicateUserNameExists){
            throw new DuplicateEntityException("User", "username", user.getUsername());
        }

        try{
            repository.getUserByEmail(user.getEmail());
        }catch (EntityNotFoundException e){
            duplicateEmailNameExists = false;
        }
        if (duplicateEmailNameExists){
            throw new DuplicateEntityException("User", "email", user.getEmail());
        }
        repository.create(user);
    }

    @Override
    public User update(User userToUpdate, User loggedUser) {
        checkUserIsBlocked(loggedUser);
        checkPermissions(userToUpdate, loggedUser);
        if(repository.isEmailExists(loggedUser.getEmail())){
            throw new DuplicateEntityException("User", "email", loggedUser.getEmail());
        }
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
    public void blockUser(int id,User user) {
        User blockUser = repository.get(id);
        checkIfAdmin(user);
        repository.blockUser(blockUser.getUsername());
    }

    @Override
    public void unblockUser(int id, User user) {
        User unblockUser = repository.get(id);
        checkIfAdmin(user);
        repository.unblockUser(unblockUser.getUsername());
    }

    @Override
    public long getCountUsers(){
        return repository.getCountUsers();
    }

    @Override
    public void giveUserAdminRights(User user, User loggedUser) {
        if(user.isBlocked()){
            repository.unblockUser(user.getUsername());
        }
        repository.giveUserAdminRights(user);
    }

    public void checkPermissions(User userToUpdate, User loggedUser) {
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
