package com.forumsystem.modelmappers;

import com.forumsystem.models.Post;
import com.forumsystem.models.Tag;
import com.forumsystem.models.modeldto.PostDto;
import com.forumsystem.models.modeldto.TagDto;
import com.forumsystem.repositories.contracts.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {

    private final PostRepository postRepository;
    private final TagMapper tagMapper;

    @Autowired
    public PostMapper(PostRepository postRepository,
                      TagMapper tagMapper) {
        this.postRepository = postRepository;
        this.tagMapper = tagMapper;
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
//        if (!post.getPostTags().isEmpty()){
    //}
        post.setPostTags(tagMapper.fromDto(postDto.getTagList()));
        post.setCreatedAt(LocalDateTime.now());
    }
}
