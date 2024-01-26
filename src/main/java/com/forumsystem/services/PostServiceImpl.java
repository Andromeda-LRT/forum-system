package com.forumsystem.services;

import com.forumsystem.modelhelpers.PostModelFilterOptions;
import com.forumsystem.models.Comment;
import com.forumsystem.repositories.PostRepository;
import com.forumsystem.repositories.UserRepository;
import com.forumsystem.еxceptions.UnauthorizedOperationException;
import com.forumsystem.models.Post;
import com.forumsystem.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.forumsystem.modelhelpers.ModelConstantHelper.*;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           UserRepository userRepository,
                           CommentService commentService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentService = commentService;
    }

//    @Override
//    public List<Post> getAllPosts(User user, PostModelFilterOptions postFilter) {
//
//
//        return postRepository.getAllPosts(user, postFilter);
//    }

    @Override
    public List<Post> getAll(User user, PostModelFilterOptions postFilter) {

        // todo to use checkIfAdmin
        return postRepository.getAll();
    }

    @Override
    public Post getById(User user, int id) {
        // todo to add JSONIgnore to post's id field
        return postRepository.getById(id);
    }

    @Override
    public void create(User user, Post post) {
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException(
                    String.format(BLOCKED_USER_ERROR_MESSAGE, POST, CREATION));
        }

        return postRepository.create(user, post);
    }

    @Override
    public void createComment(User user, Comment comment, int postId) {
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException(
                    String.format(BLOCKED_USER_ERROR_MESSAGE, COMMENT, CREATION));
        }

        commentService.create(user.getUserId(), comment, postId);
    }

    @Override
    public Post updatePost(User user, Post postToBeUpdated) {

        if (user.isBlocked()) {
            throw new UnauthorizedOperationException(
                    String.format(BLOCKED_USER_ERROR_MESSAGE, POST, EDITING));
        }

        if (!isUserOwnerOfCurrentPost(postToBeUpdated, user)) {
            throw new UnauthorizedOperationException(
                    String.format(UNAUTHORIZED_EDIT_ERROR_MESSAGE, POSTS));
        }
        return postRepository.update(postToBeUpdated);
    }

    @Override
    public Comment updateComment(User user, Comment comment, int post_id) {
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException(
                    String.format(BLOCKED_USER_ERROR_MESSAGE, COMMENT, EDITING));
        }

        return commentService.update(user, comment, post_id);
    }


    @Override
    public void delete(User user, int id) {
        // todo to use primary if statement to check whether user is admin or not
        // todo to use checkIfAdmin
        Post postToBeDeleted = postRepository.getById(id);
        if (!isUserOwnerOfCurrentPost(postToBeDeleted, user)) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_DELETION_ERROR_MESSAGE);
        }
        postRepository.delete(postToBeDeleted);
    }

    //todo to consider removing post id
    @Override
    public void deleteComment(User user, int postId, int commentId) {

        commentService.delete(user, commentId);
    }

    private boolean isUserOwnerOfCurrentPost(Post post, User user) {
        if (user != post.getCreatedBy()) {
            return false;
        }
        return true;
    }

//    private boolean isUserOwnerOfCurrentComment(User user, Comment comment) {
//        if (user != comment.getUser()) {
//            return false;
//        }
//        return true;
//    }
}
