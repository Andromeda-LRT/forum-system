package com.forumsystem.models.modeldto;

import com.forumsystem.models.Post;
import com.forumsystem.models.User;
import jakarta.persistence.*;

public class CommentResponseDto {
    private String content;
    private String createdBy;

    public CommentResponseDto() {
    }

    public CommentResponseDto(String content, String createdBy) {
        this.content = content;
        this.createdBy = createdBy;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
