package com.forumsystem.modelmappers;

import com.forumsystem.models.Post;
import com.forumsystem.models.modeldto.PostResponseDto;
import com.forumsystem.services.contracts.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostResponseMapper {

    private final CommentService commentService;

    @Autowired
    public PostResponseMapper(CommentService commentService) {
        this.commentService = commentService;
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
        dto.setComments(commentService.getAll(post.getPostId()));

        return dto;
    }
}
