package com.forumsystem.repositories;

import com.forumsystem.models.Post;
import com.forumsystem.models.User;

import java.util.List;

public interface PostRepository {
    List<Post> getAll();

    Post getById(int id);

    void create(User user, Post post);

    Post update(Post post);

    void delete(Post post);
}
