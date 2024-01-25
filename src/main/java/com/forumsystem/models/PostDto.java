package com.forumsystem.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static com.forumsystem.modelhelpers.ModelConstantHelper.*;

public class PostDto {

    @NotNull(message = EMPTY_TITLE_ERROR_MESSAGE)
    @Size(min = 16, max = 64, message = INVALID_TITLE_LENGTH_ERROR_MESSAGE)
    private String title;
    @NotNull(message = EMPTY_CONTENT_ERROR_MESSAGE)
    @Size(min = 32, max = 8192, message = INVALID_CONTENT_LENGTH_ERROR_MESSAGE)
    private String content;

    //todo to add tag field
    public PostDto() {

    }

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
