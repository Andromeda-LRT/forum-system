package com.forumsystem.services;

import com.forumsystem.repositories.PostRepository;
import com.forumsystem.еxceptions.UnauthorizedOperationException;
import com.forumsystem.models.Post;
import com.forumsystem.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.forumsystem.modelhelpers.ModelConstantHelper.UNAUTHORIZED_DELETION_ERROR_MESSAGE;
import static com.forumsystem.modelhelpers.ModelConstantHelper.UNAUTHORIZED_EDIT_ERROR_MESSAGE;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts(User user) {
        //todo since this feature is available for admins only,
        // to discuss if necessary to have validation for admin
//        if (!user.isAdmin()){
//            throw new UnauthorizedOperationException()
//        }
        return postRepository.getAll();
    }

    @Override
    public List<Post> getAllPostsCreatedByOtherUsers(User user) {
        //todo to return List for all users BUT current user from repository
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Post> getPostsForUser(User user) {
        //todo to return List for current user from repository

        throw new UnsupportedOperationException();
    }

    @Override
    public Post getById(User user, int id) {
        //todo to consider applying a validation
        return postRepository.getById(id);
    }

    @Override
    public Post create(User user, Post post) {
        //todo to discuss if post's createdBy will be set in service layer or repository

        return postRepository.create(post);
    }

    @Override
    public Post update(User user, Post postToBeUpdated) {
        if (!isUserOwnerOfCurrentPost(postToBeUpdated, user)) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_EDIT_ERROR_MESSAGE);
        }
        return postRepository.update(postToBeUpdated);
    }

    @Override
    public void delete(User user, int id) {
        Post postToBeDelete = postRepository.getById(id);
        if (!isUserOwnerOfCurrentPost(postToBeDelete, user)) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_DELETION_ERROR_MESSAGE);
        }
        //todo to discuss if Post obj will be taken in service layer or repository layer prior to deletion
        //postRepository.delete(postToBeDelete);
        postRepository.delete(id);
    }

    private boolean isUserOwnerOfCurrentPost(Post post, User user) {
        if (user != post.getCreatedBy()) {
            return false;
        }
        return true;
    }
}
