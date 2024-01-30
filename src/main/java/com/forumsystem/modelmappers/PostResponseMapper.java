package com.forumsystem.modelmappers;

import com.forumsystem.models.Post;
import com.forumsystem.models.PostResponseDto;
import org.springframework.stereotype.Component;

@Component
public class PostResponseMapper {
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
