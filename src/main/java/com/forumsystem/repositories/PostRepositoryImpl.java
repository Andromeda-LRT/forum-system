package com.forumsystem.repositories;

import com.forumsystem.models.Post;
import com.forumsystem.models.User;
import com.forumsystem.models.UserLikes;
import com.forumsystem.models.UserLikesId;
import com.forumsystem.еxceptions.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Post> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post", Post.class);
            List<Post> result = query.list();
            return result;
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

    public void likePost(int postId, int userId){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            UserLikesId userLikesId = new UserLikesId(userId, postId);
            UserLikes userLikes = session.get(UserLikes.class, userLikesId);

            if (userLikes == null) {
                userLikes = new UserLikes(userRepository.getById(userId),getById(postId), true, false);
                session.persist(userLikes);
            }
            else {
                userLikes.setLiked(true);
                userLikes.setDisliked(false);
                session.merge(userLikes);
            }
            session.getTransaction().commit();
        }
    }


    public void dislikePost(int postId, int userId){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            UserLikesId userLikesId = new UserLikesId(userId, postId);
            UserLikes userLikes = session.get(UserLikes.class, userLikesId);

            if (userLikes == null) {
                userLikes = new UserLikes(userRepository.getById(userId),getById(postId), false, true);
                session.persist(userLikes);
            }
            else {
                userLikes.setLiked(false);
                userLikes.setDisliked(true);
                session.merge(userLikes);
            }
            session.getTransaction().commit();
        }
    }

}
