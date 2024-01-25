package com.forumsystem.controllers;

import com.forumsystem.modelhelpers.AuthenticationHelper;
import com.forumsystem.modelhelpers.PostModelFilterOptions;
import com.forumsystem.modelmappers.CommentMapper;
import com.forumsystem.models.*;
import com.forumsystem.еxceptions.EntityNotFoundException;
import com.forumsystem.еxceptions.UnauthorizedOperationException;
import com.forumsystem.modelmappers.PostMapper;
import com.forumsystem.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final AuthenticationHelper authHelper;

    @Autowired
    public PostController(PostService postService,
                          PostMapper postMapper,
                          CommentMapper commentMapper,
                          AuthenticationHelper authHelper) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.commentMapper = commentMapper;
        this.authHelper = authHelper;
    }

    @GetMapping()
    public List<Post> getAllPosts(
            @RequestHeader HttpHeaders headers,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer likes,
            @RequestParam(required = false) Integer dislikes,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
        PostModelFilterOptions postFilter = new PostModelFilterOptions(
                title, likes, dislikes, sortBy, sortOrder);
        User user = authHelper.tryGetUser(headers);
        return postService.getAll(user, postFilter);
    }

//    @GetMapping("/browse")
//    public List<Post> getAll(
//            @RequestHeader HttpHeaders headers,
//            @RequestParam(required = false) String title,
//            @RequestParam(required = false) Integer likes,
//            @RequestParam(required = false) Integer dislikes,
//            @RequestParam(required = false) String sortBy,
//            @RequestParam(required = false) String sortOrder) {
//        PostModelFilterOptions postFilter = new PostModelFilterOptions(
//                title, likes, dislikes, sortBy, sortOrder);
//        User user = authHelper.tryGetUser(headers);
//        return postService.getAll(user, postFilter);
//    }

    @GetMapping("/{id}")
    public Post getById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authHelper.tryGetUser(headers);
        try {
            return postService.getById(user, id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping()
    public Post create(@RequestHeader HttpHeaders headers, @RequestBody @Valid PostDto postDto) {
        try {
            Post post = postMapper.fromDto(postDto);
            User user = authHelper.tryGetUser(headers);
            postService.create(user, post);
            return post;
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/{post_id}/comments")
    public Comment createComment(@RequestHeader HttpHeaders headers,
                                 @RequestBody @Valid CommentDto commentDto,
                                 @PathVariable int post_id) {
        try {
            User user = authHelper.tryGetUser(headers);
            Comment comment = commentMapper.fromDto(commentDto);
            postService.createComment(user, comment, post_id);
            return comment;
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Post update(@RequestHeader HttpHeaders headers,
                       @RequestBody @Valid PostDto postDto,
                       @PathVariable int id) {
        try {
            Post post = postMapper.fromDto(postDto, id);
            User user = authHelper.tryGetUser(headers);
            return postService.updatePost(user, post);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PutMapping("{post_id}/comments/{comment_id}")
    public Comment updateComment(@RequestHeader HttpHeaders headers,
                                 @RequestBody @Valid CommentDto commentDto,
                                 @PathVariable int post_id,
                                 @PathVariable int comment_id) {
        try {
            User user = authHelper.tryGetUser(headers);
            Comment comment = commentMapper.fromDto(commentDto, comment_id);
            return postService.updateComment(user, comment, post_id);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/post/{post_id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int post_id) {
        try {
            User user = authHelper.tryGetUser(headers);
            postService.delete(user, post_id);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{post_id}/comment/{comment_id}")
    public void deleteComment(@RequestHeader HttpHeaders headers,
                              @PathVariable int post_id,
                              @PathVariable int comment_id) {
        try {
            User user = authHelper.tryGetUser(headers);
            postService.deleteComment(user, post_id, comment_id);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
