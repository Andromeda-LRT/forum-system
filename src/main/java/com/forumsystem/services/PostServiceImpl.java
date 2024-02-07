package com.forumsystem.services;

import com.forumsystem.modelhelpers.PostModelFilterOptions;
import com.forumsystem.models.Comment;
import com.forumsystem.repositories.contracts.PostRepository;
import com.forumsystem.repositories.contracts.UserRepository;
import com.forumsystem.services.contracts.CommentService;
import com.forumsystem.services.contracts.PostService;
import com.forumsystem.services.contracts.TagService;
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
    private final TagService tagService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           UserRepository userRepository,
                           CommentService commentService,
                           TagService tagService
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentService = commentService;
        this.tagService = tagService;
    }

//    @Override
//    public List<Post> getAllPosts(User user, PostModelFilterOptions postFilter) {
//
//
//        return postRepository.getAllPosts(user, postFilter);
//    }

    @Override
    public List<Post> getAll(User user, PostModelFilterOptions postFilter) {

        if (!userRepository.checkIfAdmin(user.getUserId())) {
            throw new UnauthorizedOperationException(INSUFFICIENT_PERMISSIONS_ERROR_MESSAGE);
        }
        return postRepository.getAll(postFilter);
    }

    @Override
    public List<Post> getTopTenCommentedPosts() {
        return postRepository.getTopTenCommentedPosts();
    }

    @Override
    public List<Post> getTenNewestPosts() {
        return postRepository.getTenNewestPosts();
    }

    @Override
    public Long getPostCount() {
        return postRepository.getPostCount();
    }

//    public List<PostResponseDto> getAll(User user) {
////        List<Post> posts = postRepository.getAll();
////
////        List<PostResponseDto> dtoList = new ArrayList<>();
////        for (Post post : posts) {
////            dtoList.add(postResponseMapper.convertToDTO(post));
////        }
////        return dtoList;
//        throw new UnsupportedOperationException();
//
//    }

    @Override
    public Post getById(User user, int id) {

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

        if (!postToBeUpdated.getCreatedBy().equals(user) &&
                !userRepository.checkIfAdmin(user.getUserId())) {
            throw new UnauthorizedOperationException(
                    String.format(UNAUTHORIZED_EDIT_ERROR_MESSAGE, POSTS));
        }

        tagService.create(postToBeUpdated.getPostTags());
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

        Post postToBeDeleted = postRepository.getById(id);

        if (!postToBeDeleted.getCreatedBy().equals(user) &&
                !userRepository.checkIfAdmin(user.getUserId())) {
            throw new UnauthorizedOperationException(UNAUTHORIZED_DELETION_ERROR_MESSAGE);
        }

        postToBeDeleted.setIsArchived(true);

        postRepository.delete(postToBeDeleted);
    }

    //todo to consider removing post id
    @Override
    public void deleteComment(User user, int postId, int commentId) {
        Comment comment = commentService.getById(commentId);
        commentService.delete(user, comment);
    }

}
