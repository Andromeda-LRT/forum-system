package com.forumsystem.models;

import jakarta.persistence.*;
    @Entity
    @Table(name = "user_likes")
    public class UserLikes {

        @EmbeddedId
        private UserLikesId id;

        @ManyToOne
        @JoinColumn(name = "user_id", updatable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name = "post_id", updatable = false)
        private Post post;

        @Column(name = "is_liked")
        private int isLiked;

        @Column(name = "is_disliked")
        private int isDisliked;

        public UserLikes() {
        }

        public UserLikes(User user, Post post, int isLiked, int isDisliked) {
            this.id = new UserLikesId(user.getUserId(), post.getPostId());
            this.user = user;
            this.post = post;
            this.isLiked = isLiked;
            this.isDisliked = isDisliked;
        }

        public UserLikesId getId() {
            return id;
        }

        public void setId(UserLikesId id) {
            this.id = id;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Post getPost() {
            return post;
        }

        public void setPost(Post post) {
            this.post = post;
        }

        public int getIsLiked() {
            return isLiked;
        }

        public void setIsLiked(int isLiked) {
            this.isLiked = isLiked;
        }

        public int getIsDisliked() {
            return isDisliked;
        }

        public void setIsDisliked(int isDisliked) {
            this.isDisliked = isDisliked;
        }
    }
