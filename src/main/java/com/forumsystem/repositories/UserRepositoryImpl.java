package com.forumsystem.repositories;

import com.forumsystem.models.Post;
import com.forumsystem.models.User;
import com.forumsystem.еxceptions.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class)
                    .list();
        }
    }

    @Override
    public User get(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", id);
            }
            return user;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where username= :username", User.class);
            query.setParameter("username", username);
            List<User> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("User", "username", username);
            }

            return result.get(0);
        }
    }

    @Override
    public void create(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public User update(User userToUpdate) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(userToUpdate);
            session.getTransaction().commit();
            return userToUpdate;
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Post> getUserPosts(String username) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "select p from Post p join p.createdBy u where u.username = :username and p.isArchived = false ";
            Query<Post> query = session.createQuery(hql, Post.class);
            query.setParameter("username", username);
            List<Post> userPosts = query.list();
            if (userPosts.isEmpty()) {
                throw new EntityNotFoundException("User", "username", username, "posts");
            }
            return userPosts;
        }
    }

    @Override
    public void blockUser(int id, User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            String hql = "update User set isBlocked = true where username = :username";
            Query query = session.createQuery(hql);
            query.setParameter("username", user.getUsername());
            query.executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void unblockUser(int id, User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            String hql = "update User set isBlocked = false where username = :username";
            Query query = session.createQuery(hql);
            query.setParameter("username", user.getUsername());
            query.executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public boolean checkIfAdmin(int id) {
        try (Session session = sessionFactory.openSession()) {
//            String hql = "select u.userId from User u, Admin a where u.user_id = a.user_id";
//            Query query = session.createQuery(hql);
            return true;
        }
    }
}
