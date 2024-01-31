package com.forumsystem.modelmappers;

import com.forumsystem.models.Post;
import com.forumsystem.models.PostResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostResponseMapper {

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

        return dto;
    }
}
