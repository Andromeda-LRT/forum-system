package com.forumsystem.services;

import com.forumsystem.modelhelpers.PostModelFilterOptions;
import com.forumsystem.models.Comment;
import com.forumsystem.models.Post;
import com.forumsystem.models.User;
import com.forumsystem.repositories.contracts.PostRepository;
import com.forumsystem.repositories.contracts.UserRepository;
import com.forumsystem.services.contracts.CommentService;
import com.forumsystem.services.contracts.TagService;
import com.forumsystem.еxceptions.UnauthorizedOperationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.forumsystem.Helpers.createMockFilterOptions;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentService commentService;
    @Mock
    private TagService tagService;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void getAll_Should_ThrowUnauthorizedOperationException_IfUserNotAdmin() {
        // Arrange
        User user = new User();
        user.setUserId(1);
        PostModelFilterOptions postFilter = createMockFilterOptions();
        Mockito.when(userRepository.checkIfAdmin(user.getUserId())).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> postService.getAll(user, postFilter));
        Mockito.verify(userRepository, Mockito.times(1)).checkIfAdmin(user.getUserId());
    }

    @Test
    void getAll_Should_ReturnPosts_IfUserIsAdmin() {
        // Arrange
        User user = new User();
        user.setUserId(1);
        PostModelFilterOptions postFilter = createMockFilterOptions();
        Mockito.when(userRepository.checkIfAdmin(user.getUserId())).thenReturn(true);
        Mockito.when(postRepository.getAll(postFilter)).thenReturn(Arrays.asList(new Post(), new Post()));

        // Act
        List<Post> result = postService.getAll(user, postFilter);

        // Assert
        Mockito.verify(postRepository).getAll(postFilter);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void create_Should_ThrowUnauthorizedOperationException_IfUserBlocked() {
        // Arrange
        User user = new User();
        user.setBlocked(true);
        Post post = new Post();

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> postService.create(user, post));
    }

    @Test
    void create_Should_SavePost_IfUserNotBlocked() {
        // Arrange
        User user = new User();
        Post post = new Post();
        Mockito.doNothing().when(postRepository).create(any(User.class), any(Post.class));

        // Act
        postService.create(user, post);

        // Assert
        Mockito.verify(postRepository, Mockito.times(1)).create(eq(user), eq(post));
    }

    @Test
    void updatePost_Should_ThrowUnauthorizedOperationException_IfUserBlockedOrNotOwnerOrAdmin() {
        // Arrange
        User user = new User();
        user.setBlocked(true);
        Post post = new Post();

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> postService.updatePost(user, post));
    }

    @Test
    void getTopTenCommentedPosts_Should_CallRepositoryMethod() {
        // Arrange
        Mockito.when(postRepository.getTopTenCommentedPosts()).thenReturn(Arrays.asList(new Post(), new Post()));

        // Act
        List<Post> result = postService.getTopTenCommentedPosts();

        // Assert
        Mockito.verify(postRepository, Mockito.times(1)).getTopTenCommentedPosts();
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void getTenNewestPosts_Should_ReturnTenPosts() {
        // Arrange
        Mockito.when(postRepository.getTenNewestPosts()).thenReturn(Arrays.asList(new Post()));

        // Act
        List<Post> result = postService.getTenNewestPosts();

        // Assert
        Mockito.verify(postRepository, Mockito.times(1)).getTenNewestPosts();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void getPostCount_Should_ReturnCount() {
        // Arrange
        Mockito.when(postRepository.getPostCount()).thenReturn(5L);

        // Act
        Long count = postService.getPostCount();

        // Assert
        Mockito.verify(postRepository, Mockito.times(1)).getPostCount();
        Assertions.assertEquals(5, count);
    }


    @Test
    void getById_Should_ReturnPost() {
        // Arrange
        User user = new User();
        int postId = 1;
        Post post = new Post();
        Mockito.when(postRepository.getById(postId)).thenReturn(post);

        // Act
        Post result = postService.getById(user, postId);

        // Assert
        Mockito.verify(postRepository, Mockito.times(1)).getById(postId);
        Assertions.assertEquals(post, result);
    }

    @Test
    void createComment_Should_ThrowUnauthorizedOperationException_IfUserBlocked() {
        // Arrange
        User user = new User();
        user.setBlocked(true);
        Comment comment = new Comment();
        int postId = 1;

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> postService.createComment(user, comment, postId));
    }

    @Test
    void createComment_Should_CallCommentServiceCreate_IfUserNotBlocked() {
        // Arrange
        User user = new User();
        Comment comment = new Comment();
        int postId = 1;
        Mockito.doNothing().when(commentService).create(anyInt(), any(Comment.class), eq(postId));

        // Act
        postService.createComment(user, comment, postId);

        // Assert
        Mockito.verify(commentService, Mockito.times(1)).create(eq(user.getUserId()), eq(comment), eq(postId));
    }

    @Test
    void updateComment_Should_ThrowUnauthorizedOperationException_IfUserBlocked() {
        // Arrange
        User user = new User();
        user.setBlocked(true);
        Comment comment = new Comment();
        int postId = 1;

        // Act & Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> postService.updateComment(user, comment, postId));
    }

    @Test
    void delete_Should_DeletePost_IfUserIsOwnerOrAdmin() {
        // Arrange
        User user = new User();
        Post post = new Post();
        post.setCreatedBy(user);
        Mockito.when(postRepository.getById(anyInt())).thenReturn(post);
        Mockito.doNothing().when(postRepository).delete(any(Post.class));

        // Act
        postService.delete(user, post.getPostId());

        // Assert
        Mockito.verify(postRepository, Mockito.times(1)).delete(post);
    }

    @Test
    void dislikePost_Should_CallRepository() {
        // Arrange
        User user = new User();
        user.setUserId(1);
        Post post = new Post();
        post.setPostId(1);
        Mockito.doNothing().when(postRepository).dislikePost(post.getPostId(), user.getUserId());

        // Act
        postService.dislikePost(post, user);

        // Assert
        Mockito.verify(postRepository, Mockito.times(1)).dislikePost(post.getPostId(), user.getUserId());
    }

    @Test
    void likePost_Should_CallRepository() {
        // Arrange
        User user = new User();
        user.setUserId(1);
        Post post = new Post();
        post.setPostId(1);
        Mockito.doNothing().when(postRepository).likePost(post.getPostId(), user.getUserId());

        // Act
        postService.likePost(post, user);

        // Assert
        Mockito.verify(postRepository, Mockito.times(1)).likePost(post.getPostId(), user.getUserId());
    }
}