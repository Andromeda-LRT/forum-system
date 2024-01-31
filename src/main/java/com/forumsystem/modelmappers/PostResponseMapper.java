package com.forumsystem.modelmappers;

import com.forumsystem.models.Post;
import com.forumsystem.models.Tag;
import com.forumsystem.models.modeldto.PostResponseDto;
import com.forumsystem.models.modeldto.TagDto;
import com.forumsystem.services.contracts.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PostResponseMapper {

    private final CommentService commentService;

    private final TagMapper tagMapper;
    @Autowired
    public PostResponseMapper(CommentService commentService, TagMapper tagMapper) {
        this.commentService = commentService;
        this.tagMapper = tagMapper;
    }


    public List<PostResponseDto> convertToDTO(List<Post> postList) {
        List<PostResponseDto> result = new ArrayList<>();
        for (Post postLocal : postList) {
            result.add(convertToDTO(postLocal));
        }
        return result;
    }

    public PostResponseDto convertToDTO(Post post) {
        PostResponseDto dto = new PostResponseDto();

        dto.setCreatedBy(post.getCreatedBy().getUsername());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setLikes(post.getLikes());
        dto.setDislikes(post.getDislikes());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setTags(convertTagList(post.getPostTags()));
        dto.setComments(commentService.getAll(post.getPostId()));

        return dto;
    }
    private Set<TagDto> convertTagList(Set<Tag> tags){
        Set<TagDto> result = new HashSet<>();
        if(tags!=null) {
            for (Tag tag : tags) {
                TagDto dto = new TagDto();
                dto.setName(tag.getName());
                result.add(dto);
            }
        }
        return result;
    }
}
