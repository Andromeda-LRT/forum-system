package com.forumsystem.repositories.contracts;

import com.forumsystem.models.Comment;
import com.forumsystem.models.Post;

import java.util.List;

public interface CommentRepository {
    List<Comment> getAll();

    Comment getById(int id);

    void create(Comment comment);

    void update(Comment comment);

    void delete(Comment comment);
}
