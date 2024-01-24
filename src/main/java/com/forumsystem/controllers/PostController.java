package com.forumsystem.controllers;

import com.forumsystem.еxceptions.UnauthorizedOperationException;
import com.forumsystem.modelmappers.PostMapper;
import com.forumsystem.models.Post;
import com.forumsystem.models.PostDto;
import com.forumsystem.models.User;
import com.forumsystem.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    @Autowired
    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping("/admin")
    public List<Post> getAllPosts(User user) {
        return postService.getAllPosts(user);
    }

    @GetMapping("/posts")
    List<Post> getAllPostsCreatedByOtherUsers(String username) {
        //todo to find user based on their username for starters or consider an alternative method
        // return postService.getAllPostsCreatedByOtherUsers()
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{username}/posts")
    List<Post> getPostsForUser(@PathVariable String username) {
        //todo to find user based on their username for starters or consider an alternative method
        // postService.getPostsForUser()
        throw new UnsupportedOperationException();
    }

    //todo user should be authenticated first
    @PostMapping()
    Post create(User user, @RequestBody @Valid PostDto postDto) {
        Post post = postMapper.fromDto(postDto);
        return postService.create(user, post);
    }

    @PutMapping
    Post update(User user, @RequestBody @Valid PostDto postDto) {
        try {
            Post post = postMapper.fromDto(postDto);
            return postService.update(user, post);
        } catch (UnauthorizedOperationException uoe) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, uoe.getMessage());
        }
    }

    // todo to decide on soft or hard delete
    @DeleteMapping
    void delete(User user, int id) {
        try {
            postService.delete(user, id);
        } catch (UnauthorizedOperationException uoe) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, uoe.getMessage());
        }
    }
}
