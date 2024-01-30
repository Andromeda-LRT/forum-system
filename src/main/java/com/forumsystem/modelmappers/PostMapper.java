package com.forumsystem.modelmappers;

import com.forumsystem.models.Post;
import com.forumsystem.models.PostDto;
import com.forumsystem.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {

    private final PostRepository postRepository;

    @Autowired
    public PostMapper(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post fromDto(PostDto postDto, int id){
        Post post = postRepository.getById(id);
        dtoToObj(post, postDto);
        return post;
    }
    public Post fromDto(PostDto postDto) {
        Post post = new Post();
        dtoToObj(post, postDto);
        return post;
    }

    private void dtoToObj(Post post, PostDto postDto) {
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
    }
}
