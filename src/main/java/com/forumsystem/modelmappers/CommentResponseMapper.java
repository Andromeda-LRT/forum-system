package com.forumsystem.modelmappers;

import com.forumsystem.models.Comment;
import com.forumsystem.models.Post;
import com.forumsystem.models.modeldto.CommentResponseDto;
import com.forumsystem.models.modeldto.PostResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CommentResponseMapper {

    public CommentResponseDto convertToDTO(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();

        dto.setContent(comment.getContent());
        dto.setCreatedBy(comment.getUser().getUsername());

        return dto;
    }
}
