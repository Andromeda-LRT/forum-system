package com.forumsystem.repositories;

import com.forumsystem.models.Post;

import java.util.List;

public interface PostRepository {
    List<Post> getAll();

    Post getById(int id);

    Post create(Post post);

    Post update(int id);
}
