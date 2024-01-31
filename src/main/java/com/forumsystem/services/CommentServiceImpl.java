package com.forumsystem.services;

import com.forumsystem.models.Comment;
import com.forumsystem.models.Post;
import com.forumsystem.models.User;
import com.forumsystem.repositories.contracts.CommentRepository;
import com.forumsystem.repositories.contracts.PostRepository;
import com.forumsystem.repositories.contracts.UserRepository;
import com.forumsystem.services.contracts.CommentService;
import com.forumsystem.еxceptions.UnauthorizedOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.forumsystem.modelhelpers.ModelConstantHelper.COMMENTS;
import static com.forumsystem.modelhelpers.ModelConstantHelper.UNAUTHORIZED_EDIT_ERROR_MESSAGE;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
    //todo need to add implementation
    @Override
    public List<Comment> getAll() {
        return null;
    }

    @Override
    public Comment getById(int id) {
        return commentRepository.getById(id);
    }

    @Override
    public void create(int user_id, Comment comment, int post_id) {
        User user = userRepository.get(user_id);
        Post post = postRepository.getById(post_id);
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.create(comment);
    }

    @Override
    public void update(User user, Comment comment) {
        if(!comment.getUser().equals(user) &&
                !userRepository.checkIfAdmin(user.getUserId())){
            throw new UnauthorizedOperationException(
                    String.format(UNAUTHORIZED_EDIT_ERROR_MESSAGE, COMMENTS));
        }
         commentRepository.update(comment);
    }

    @Override
    public void delete(User user, Comment comment) {
        if(!comment.getUser().equals(user) &&
                !userRepository.checkIfAdmin(user.getUserId())){
            throw new UnauthorizedOperationException(
                    String.format(UNAUTHORIZED_EDIT_ERROR_MESSAGE, COMMENTS));
        }
        comment.setArchived(true);

        commentRepository.delete(comment);
    }
}
