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
import com.forumsystem.еxceptions.EntityNotFoundException;
import com.forumsystem.еxceptions.UnauthorizedOperationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostMvcController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final AuthenticationHelper authHelper;
    private final PostResponseMapper postResponseMapper;
    private final CommentService commentService;
    private final TagService tagService;
    /**
     * temporary adding userService in order to hardcode several CRUD operations
     * prior to refactoring with MVC authentication
     */
    private final UserService userService;

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

    @GetMapping()
    public String ShowAllPosts(
            Model model,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer likes,
            @RequestParam(required = false) Integer dislikes,
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
    ) {

        //ToDo authenticate user prior to executing operation

        PostModelFilterOptions postFilter = new PostModelFilterOptions(
                title, likes, dislikes, tagName, sortBy, sortOrder);
        User loggedUser = userService.getUserByUsername("john_doe");
        List<Post> posts = postService.getAll(loggedUser, postFilter);
        List<PostResponseDto> outputPosts = postResponseMapper.convertToDTO(posts);
        model.addAttribute("posts", outputPosts);
        return "PostsView";
    }


    @GetMapping("/{id}")
    public String showSinglePost(@PathVariable int id, Model model) {

        //ToDo authenticate user prior to executing operation

        User loggedUser = userService.getUserByUsername("john_doe");
        try {
            PostResponseDto post = postResponseMapper
                    .convertToDTO(postService.getById(loggedUser, id));
            List<CommentResponseDto> postComments = commentService.getAll(id);

            model.addAttribute("post", post);
            model.addAttribute("postComments", postComments);
            return "PostView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("/new")
    public String showCreatePostPage(Model model) {

        //ToDo authenticate user prior to executing operation

        model.addAttribute("post", new PostDto());
        return "post-new";
    }


    @PostMapping("/new")
    public String createPost(@Valid @ModelAttribute("post") PostDto postDto,
                             BindingResult errors,
                             Model model) {

        //ToDo authenticate user prior to executing operation

        if (errors.hasErrors()) {
            return "post-new";
        }

        try {
            //todo to rework with MVC authentication
            User creator = userService.getUserByUsername("john_doe");
            Post post = postMapper.fromDto(postDto);
            postService.create(creator, post);
            return "redirect:/posts";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "TBD";   // short for TO BE DECIDED
        }
    }

    @GetMapping("/{id}/update")
    public String showEditPostPage(@PathVariable int id, Model model) {

        //ToDo authenticate user prior to executing operation
        User loggedUser = userService.getUserByUsername("john_doe");
        Post post = postService.getById(loggedUser, id);
        PostResponseDto outputPost = postResponseMapper.convertToDTO(post);
        model.addAttribute("post", outputPost);
        return "PostUpdateView";
    }

    @PostMapping("/{id}/update")
    public String updatePost(@PathVariable int id,
                             @Valid @ModelAttribute("post") PostDto postDto,
                             BindingResult errors,
                             Model model) {
        //ToDo authenticate user prior to executing operation


        if (errors.hasErrors()) {
            return "post-update";
        }
        try {
            User creator = userService.getUserByUsername("john_doe");
            Post newPost = postMapper.fromDto(postDto, id);
            postService.updatePost(creator, newPost);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("/{id}/delete")
    public String deletePost(@PathVariable int id, Model model, HttpSession session) {
        //ToDo authenticate user prior to executing operation

        try {
            User user = userService.getUserByUsername("john_doe");
            postService.delete(user, id);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "TBD";   // short for TO BE DECIDED
        }
    }

    @GetMapping("/{id}/like")
     String likePost(@PathVariable int id, Model model, HttpSession session) {
        //ToDo authenticate user prior to executing operation

        try {
            User user = userService.getUserByUsername("john_doe");
            Post post = postService.getById(user, id);
            postService.likePost(post, user);
            return "PostView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("/{id}/dislike")
    public String dislikePost(@PathVariable int id, Model model, HttpSession session) {
        //ToDo authenticate user prior to executing operation

        try {
            User user = userService.getUserByUsername("john_doe");
            Post post = postService.getById(user, id);
            postService.dislikePost(post, user);
            return "PostView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("/{post_id}/newComment")
    public String showCreateCommentPage(@PathVariable int post_id, Model model, HttpSession session) {
        model.addAttribute("comment", new CommentDto());
        //todo the below view to re-use everything from show a single post with the addition
        // of a field that will be used for the comment of the post.

        try {
            User user = userService.getUserByUsername("john_doe");
            postService.getById(user, post_id);
            return "CreatePostCommentView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        }
    }

    @PostMapping("/{post_id}/newComment")
    public String createPostComment(@Valid @ModelAttribute("comment") CommentDto commentDto,
                                    @PathVariable int post_id,
                                    BindingResult errors,
                                    Model model,
                                    HttpSession session) {

        //ToDo authenticate user prior to executing operation
        if (errors.hasErrors()) {
            return "CreatePostCommentView";
        }

        try {
            User user = userService.getUserByUsername("john_doe");
            Comment comment = commentMapper.fromDto(commentDto);
            postService.createComment(user, comment, post_id);
            // after successful comment creation user to be returned on page where the post
            // for which he created a comment is.
            return "PostView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "TBD";   // short for TO BE DECIDED
        }
    }

    @GetMapping("/{post_id}/comment/{comment_id}/update")
    public String showEditPostCommentPage(@PathVariable int post_id,
                                          @PathVariable int comment_id,
                                          Model model,
                                          HttpSession session) {
        //ToDo authenticate user prior to executing operation

        try {
            User user = userService.getUserByUsername("john_doe");
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
                                    HttpSession session){

        //ToDo authenticate user prior to executing operation

        if (errors.hasErrors()){
            return "EditPostCommentView";
        }

        try {
            User user = userService.getUserByUsername("john_doe");
            Comment newComment = commentMapper.fromDto(commentDto, comment_id);
            postService.updateComment(user, newComment, post_id);
            return "PostView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "ErrorView";
        } catch (UnauthorizedOperationException e){
            model.addAttribute("error", e.getMessage());
            return "TBD";   // short for TO BE DECIDED
        }
    }

    @GetMapping("/{post_id}/comment/{comment_id}/delete")
    public String deletePostComment(@PathVariable int post_id,
                                    @PathVariable int comment_id,
                                    Model model,
                                    HttpSession session){

        //ToDo authenticate user prior to executing operation

        try {
            User user = userService.getUserByUsername("john_doe");
            postService.deleteComment(user, post_id, comment_id);
            return "PostView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (UnauthorizedOperationException e){
            model.addAttribute("error", e.getMessage());
            return "TBD";   // short for TO BE DECIDED
        }
    }
}
