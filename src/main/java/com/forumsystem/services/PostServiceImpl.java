package com.forumsystem.services;

import com.forumsystem.modelhelpers.PostModelFilterOptions;
import com.forumsystem.modelmappers.PostResponseMapper;
import com.forumsystem.models.Comment;
import com.forumsystem.models.PostResponseDto;
import com.forumsystem.repositories.PostRepository;
import com.forumsystem.repositories.UserRepository;
import com.forumsystem.еxceptions.UnauthorizedOperationException;
import com.forumsystem.models.Post;
import com.forumsystem.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.forumsystem.modelhelpers.ModelConstantHelper.*;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;

    private final PostResponseMapper postResponseMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           UserRepository userRepository,
                           CommentService commentService, PostResponseMapper postResponseMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentService = commentService;
        this.postResponseMapper = postResponseMapper;
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

    public List<PostResponseDto> getAll(User user) {
        List<Post> posts = postRepository.getAll();

        List<PostResponseDto> dtoList = new ArrayList<>();
        for (Post post : posts) {
            dtoList.add(postResponseMapper.convertToDTO(post));
        }
        return dtoList;

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

        postRepository.create(user, post);
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
    public void updateComment(User user, Comment comment, int post_id) {
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException(
                    String.format(BLOCKED_USER_ERROR_MESSAGE, COMMENT, EDITING));
        }

        commentService.update(user, comment);
    }

    @Override
    public void likePost(Post post, User user) {
       postRepository.likePost(post.getPostId(), user.getUserId());
    }

    @Override
    public void dislikePost(Post post, User user) {
        postRepository.dislikePost(post.getPostId(), user.getUserId());
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
        Comment comment = commentService.getById(commentId);
        commentService.delete(user, comment);
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
