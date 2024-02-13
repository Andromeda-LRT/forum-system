package com.forumsystem.controllers.mvc;

import com.forumsystem.modelhelpers.AuthenticationHelper;
import com.forumsystem.modelhelpers.PostModelFilterOptions;
import com.forumsystem.modelmappers.CommentMapper;
import com.forumsystem.modelmappers.PostMapper;
import com.forumsystem.modelmappers.PostResponseMapper;
import com.forumsystem.models.*;
import com.forumsystem.models.modeldto.CommentDto;
import com.forumsystem.models.modeldto.CommentResponseDto;
import com.forumsystem.models.modeldto.PostDto;
import com.forumsystem.models.modeldto.PostResponseDto;
import com.forumsystem.services.contracts.CommentService;
import com.forumsystem.services.contracts.PostService;
import com.forumsystem.services.contracts.TagService;
import com.forumsystem.services.contracts.UserService;
import com.forumsystem.еxceptions.AuthenticationFailureException;
import com.forumsystem.еxceptions.EntityNotFoundException;
import com.forumsystem.еxceptions.UnauthorizedOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostMvcController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final AuthenticationHelper authHelper;
    private final PostResponseMapper postResponseMapper;
    private final CommentService commentService;
    private final UserService userService;
    private final TagService tagService;

    @Autowired
    public PostMvcController(PostService postService,
                             PostMapper postMapper,
                             CommentService commentService,
                             CommentMapper commentMapper,
                             AuthenticationHelper authHelper,
                             PostResponseMapper postResponseMapper,
                             TagService tagService,
                             UserService userService) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.commentService = commentService;
        this.commentMapper = commentMapper;
        this.authHelper = authHelper;
        this.postResponseMapper = postResponseMapper;
        this.tagService = tagService;
        this.userService = userService;
    }

//
//    @ModelAttribute("tags")
//    public List<Tag> populateTags() {
//        return tagService.getAll();
//    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }
    @GetMapping()
    public String ShowAllPosts(
            Model model,
            HttpSession session,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer likes,
            @RequestParam(required = false) Integer dislikes,
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
    ) {

        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        PostModelFilterOptions postFilter = new PostModelFilterOptions(
                title, likes, dislikes, tagName, sortBy, sortOrder);

        List<Post> posts = postService.getAll(user, postFilter);
        List<PostResponseDto> outputPosts = postResponseMapper.convertToDTO(posts);
        model.addAttribute("posts", outputPosts);
        return "PostsView";
    }


    @GetMapping("/{id}")
    public String showSinglePost(@PathVariable int id,
                                 Model model,
                                 HttpSession session) {

        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            PostResponseDto post = postResponseMapper
                    .convertToDTO(postService.getById(user, id));
            List<CommentResponseDto> postComments = commentService.getAll(id);

            model.addAttribute("postId", id);
            model.addAttribute("post", post);
            model.addAttribute("postToCompare", postService.getById(user, id));
            model.addAttribute("postComments", postComments);
            model.addAttribute("userPosts", userService.getUserPosts(user.getUsername()));
            model.addAttribute("user", user);

            return "PostView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "UnauthorizedView";
        }
    }

    @GetMapping("/new")
    public String showCreatePostPage(Model model, HttpSession session) {

        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        model.addAttribute("post", new PostDto());
        return "post-new";
    }


    @PostMapping("/new")
    public String createPost(@Valid @ModelAttribute("post") PostDto postDto,
                             HttpSession session,
                             BindingResult errors,
                             Model model) {

        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        if (errors.hasErrors()) {
            return "redirect:/posts/new";
        }

        try {
            Post post = postMapper.fromDto(postDto);
            postService.create(user, post);
            return "redirect:/posts";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "UnauthorizedView";
        }
    }

    @GetMapping("/{id}/update")
    public String showEditPostPage(@PathVariable int id,
                                   Model model,
                                   HttpSession session) {

        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        Post post = postService.getById(user, id);
        PostResponseDto outputPost = postResponseMapper.convertToDTO(post);
        model.addAttribute("post", outputPost);
        return "PostUpdateView";
    }

    @PostMapping("/{id}/update")
    public String updatePost(@PathVariable int id,
                             @Valid @ModelAttribute("post") PostDto postDto,
                             HttpSession session,
                             BindingResult errors,
                             Model model) {
        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        if (errors.hasErrors()) {
            return "redicted:/posts/" + id + "update";
        }
        try {
            Post newPost = postMapper.fromDto(postDto, id);
            postService.updatePost(user, newPost);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("/{id}/delete")
    public String deletePost(@PathVariable int id, Model model, HttpSession session) {

        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        try {
            postService.delete(user, id);
            if (userService.checkIfAdmin(user)) {
                return "redirect:/admin/posts";
            }
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "UnauthorizedView";
        }
    }

    @GetMapping("/{id}/like")
    String likePost(@PathVariable int id, Model model, HttpSession session) {

        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            Post post = postService.getById(user, id);
            postService.likePost(post, user);
            return "redirect:/posts/" + id;
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("/{id}/dislike")
    public String dislikePost(@PathVariable int id, Model model, HttpSession session) {

        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            Post post = postService.getById(user, id);
            postService.dislikePost(post, user);
            return "redirect:/posts/" + id;
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("/{post_id}/newComment")
    public String showCreateCommentPage(@PathVariable int post_id,
                                        Model model,
                                        HttpSession session) {
//        model.addAttribute("comment", new CommentDto());
        //todo the below view to re-use everything from show a single post with the addition
        // of a field that will be used for the comment of the post.

        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            model.addAttribute("comment", new CommentDto());
            model.addAttribute("post", postService.getById(user, post_id));
            return "CreatePostCommentView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @PostMapping("/{post_id}/newComment")
    public String createPostComment(@PathVariable int post_id,
                                    @Valid @ModelAttribute("comment") CommentDto commentDto,
                                    BindingResult errors,
                                    Model model,
                                    HttpSession session) {

        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        if (errors.hasErrors()) {
            model.addAttribute("post", postService.getById(user, post_id));
            return "CreatePostCommentView";
        }

        try {
            Comment comment = commentMapper.fromDto(commentDto);
            postService.createComment(user, comment, post_id);
            // after successful comment creation user to be returned on page where the post
            // for which he created a comment is.
            return "redirect:/posts/" + post_id;
            // todo// return redirect://{post_id}
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "UnauthorizedView";
        }
    }

    @GetMapping("/{post_id}/comment/{comment_id}/update")
    public String showEditPostCommentPage(@PathVariable int post_id,
                                          @PathVariable int comment_id,
                                          Model model,
                                          HttpSession session) {
        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            postService.getById(user, post_id);
            Comment comment = commentService.getById(comment_id);
            CommentDto commentDto = commentMapper.toDto(comment);
            model.addAttribute("commentId", comment_id);
            model.addAttribute("comment", commentDto);
            return "EditPostCommentView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @PostMapping("/{post_id}/comment/{comment_id}/update")
    public String updatePostComment(@PathVariable int post_id,
                                    @PathVariable int comment_id,
                                    @Valid @ModelAttribute("comment") CommentDto commentDto,
                                    Model model,
                                    BindingResult errors,
                                    HttpSession session) {

        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        if (errors.hasErrors()) {
            return "redirect:/posts/" + post_id + "/comment/" + comment_id + "/update";
        }

        try {
            Comment newComment = commentMapper.fromDto(commentDto, comment_id);
            postService.updateComment(user, newComment, post_id);
            return "PostView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "UnauthorizedView";
        }
    }

    @GetMapping("/{post_id}/comment/{comment_id}/delete")
    public String deletePostComment(@PathVariable int post_id,
                                    @PathVariable int comment_id,
                                    Model model,
                                    HttpSession session) {

        User user;
        try {
            user = authHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            postService.deleteComment(user, post_id, comment_id);
            return "redirect:/posts/" + post_id;
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "UnauthorizedView";
        }
    }
}
