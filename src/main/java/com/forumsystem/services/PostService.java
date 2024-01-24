package com.forumsystem.services;

import com.forumsystem.models.Post;
import com.forumsystem.models.PostDto;
import com.forumsystem.models.User;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts(User user);
    List<Post> getAllPostsCreatedByOtherUsers(User user);
    List<Post> getPostsForUser(User user);

    Post getById(User user, int id);

    Post create(User user, Post post);

    Post update(User user, Post post);

    void delete(User user, int id);
}
