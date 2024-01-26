package com.forumsystem.services;

import com.forumsystem.models.Comment;
import com.forumsystem.models.User;

import java.util.List;

public interface CommentService {

    List<Comment> getAll();

    Comment getById(int id);

    void create(int user_id, Comment comment, int post_id);

    void update(User user, Comment comment);

    void delete(User user, Comment comment);
}
