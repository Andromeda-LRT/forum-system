package com.forumsystem.repositories;

import com.forumsystem.modelhelpers.PostModelFilterOptions;
import com.forumsystem.models.Post;
import com.forumsystem.models.User;
import com.forumsystem.models.UserLikes;
import com.forumsystem.models.UserLikesId;
import com.forumsystem.repositories.contracts.PostRepository;
import com.forumsystem.repositories.contracts.UserRepository;
import com.forumsystem.еxceptions.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final SessionFactory sessionFactory;
    private final UserRepository userRepository;

    @Autowired
    public PostRepositoryImpl(SessionFactory sessionFactory, UserRepository userRepository) {
        this.sessionFactory = sessionFactory;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> getAll(PostModelFilterOptions filterOptions) {
        try (Session session = sessionFactory.openSession()) {

            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            filterOptions.getTitle().ifPresent(value -> {
                filters.add("title like :title");
                params.put("title", String.format("%%%s%%", value));
            });

            filterOptions.getLikes().ifPresent(value -> {
                filters.add("likes = :likes");
                params.put("likes", value);
            });

            filterOptions.getDislikes().ifPresent(value -> {
                filters.add("dislikes = :dislikes");
                params.put("dislikes", value);
            });

            filterOptions.getTagName().ifPresent(value -> {
                filters.add("name = :tagName");
                params.put("tagName", value);
            });

            StringBuilder queryString = new StringBuilder("from Post p join p.postTags as pt");

            if (!filters.isEmpty()) {
                queryString
                        .append(" where ")
                        .append(String.join(" and ", filters));
            }
            queryString.append(generateOrderBy(filterOptions));

            Query<Post> query = session.createQuery(queryString.toString(), Post.class);
            query.setProperties(params);
            return query.list();
        }
    }

    @Override
    public Post getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Post post = session.get(Post.class, id);
            if (post == null) {
                throw new EntityNotFoundException("Post", id);
            }
            return post;
        }
    }

    @Override
    public void create(User user, Post post) {
        post.setCreatedBy(user);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(post);
            session.getTransaction().commit();

        }
    }

    @Override
    public Post update(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(post);
            session.getTransaction().commit();

        }
        return post;
    }

    @Override
    public void delete(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(post);
            session.getTransaction().commit();
        }
    }

    @Override
    public void likePost(int postId, int userId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            UserLikesId userLikesId = new UserLikesId(userId, postId);
            UserLikes userLikes = session.get(UserLikes.class, userLikesId);

            if (userLikes == null) {
                userLikes = new UserLikes(userRepository.get(userId), getById(postId), true, false);
                session.persist(userLikes);
            } else {
                userLikes.setLiked(true);
                userLikes.setDisliked(false);
                session.merge(userLikes);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public void dislikePost(int postId, int userId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            UserLikesId userLikesId = new UserLikesId(userId, postId);
            UserLikes userLikes = session.get(UserLikes.class, userLikesId);

            if (userLikes == null) {
                userLikes = new UserLikes(userRepository.get(userId), getById(postId), false, true);
                session.persist(userLikes);
            } else {
                userLikes.setLiked(false);
                userLikes.setDisliked(true);
                session.merge(userLikes);
            }
            session.getTransaction().commit();
        }
    }

    private String generateOrderBy(PostModelFilterOptions filterOptions) {

        if (filterOptions.getSortBy().isEmpty()) {
            return "";
        }

        String orderBy = "";
        switch (filterOptions.getSortBy().get()) {
            case "title":
                orderBy = "title";
                break;
            case "likes":
                orderBy = "likes";
                break;
            case "dislikes":
                orderBy = "dislikes";
                break;
        }
        orderBy = String.format(" order by %s", orderBy);

        if (filterOptions.getSortOrder().isPresent() &&
                filterOptions.getSortOrder().get().equalsIgnoreCase("desc")) {
            orderBy = String.format("%s desc", orderBy);
        }

        return orderBy;
    }

}
