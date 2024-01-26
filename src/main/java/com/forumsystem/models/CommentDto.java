package com.forumsystem.models;

import jakarta.validation.constraints.NotNull;

public class CommentDto {
    @NotNull
    private String content;

    public CommentDto() {

    }

    public CommentDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
