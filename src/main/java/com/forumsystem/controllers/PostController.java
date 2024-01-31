package com.forumsystem.controllers;

import com.forumsystem.modelhelpers.AuthenticationHelper;
import com.forumsystem.modelhelpers.PostModelFilterOptions;
import com.forumsystem.modelmappers.CommentMapper;
import com.forumsystem.modelmappers.PostResponseMapper;
import com.forumsystem.models.*;
import com.forumsystem.models.modeldto.CommentDto;
import com.forumsystem.models.modeldto.PostDto;
import com.forumsystem.models.modeldto.PostResponseDto;
import com.forumsystem.еxceptions.EntityNotFoundException;
import com.forumsystem.еxceptions.UnauthorizedOperationException;
import com.forumsystem.modelmappers.PostMapper;
import com.forumsystem.services.contracts.PostService;
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
    private final PostResponseMapper postResponseMapper;

    @Autowired
    public PostController(PostService postService,
                          PostMapper postMapper,
                          CommentMapper commentMapper,
                          AuthenticationHelper authHelper,
                          PostResponseMapper postResponseMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.commentMapper = commentMapper;
        this.authHelper = authHelper;
        this.postResponseMapper = postResponseMapper;
    }

    @GetMapping()
    public List<PostResponseDto> getAllPosts(
            @RequestHeader HttpHeaders headers,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer likes,
            @RequestParam(required = false) Integer dislikes,
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
        PostModelFilterOptions postFilter = new PostModelFilterOptions(
                title, likes, dislikes, tagName, sortBy, sortOrder);
        try {
            User user = authHelper.tryGetUser(headers);
            List<Post> postList = postService.getAll(user, postFilter);
            return postResponseMapper.convertToDTO(postList);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

//    public List<PostResponseDto> getAllPosts(@RequestHeader HttpHeaders headers) {
//        User user = authHelper.tryGetUser(headers);
//        return postService.getAll(user);
//    }

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
    public PostResponseDto getById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authHelper.tryGetUser(headers);
        try {
            Post post = postService.getById(user, id);
            return postResponseMapper.convertToDTO(post);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping()
    public PostResponseDto create(@RequestHeader HttpHeaders headers, @RequestBody @Valid PostDto postDto) {
        try {
            Post post = postMapper.fromDto(postDto);
            User user = authHelper.tryGetUser(headers);
            postService.create(user, post);
            return postResponseMapper.convertToDTO(post);
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
    public PostResponseDto update(@RequestHeader HttpHeaders headers,
                                  @RequestBody @Valid PostDto postDto,
                                  @PathVariable int id) {
        try {
            Post post = postMapper.fromDto(postDto, id);
            User user = authHelper.tryGetUser(headers);
            postService.updatePost(user, post);
            return postResponseMapper.convertToDTO(post);
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
            postService.updateComment(user, comment, post_id);
            return comment;
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("{post_id}/like")
    public void likePost(@RequestHeader HttpHeaders headers,
                         @PathVariable int post_id) {
        User user = authHelper.tryGetUser(headers);
        Post post = postService.getById(user, post_id);
        postService.likePost(post, user);
    }

    @PutMapping("{post_id}/dislike")
    public void dislikePost(@RequestHeader HttpHeaders headers,
                            @PathVariable int post_id) {
        User user = authHelper.tryGetUser(headers);
        Post post = postService.getById(user, post_id);
        postService.dislikePost(post, user);
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
