package com.forumsystem.models.modeldto;

import com.forumsystem.models.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

import static com.forumsystem.modelhelpers.ModelConstantHelper.*;

public class PostDto {

    @NotNull(message = EMPTY_TITLE_ERROR_MESSAGE)
    @Size(min = 16, max = 64, message = INVALID_TITLE_LENGTH_ERROR_MESSAGE)
    private String title;
    @NotNull(message = EMPTY_CONTENT_ERROR_MESSAGE)
    @Size(min = 32, max = 8192, message = INVALID_CONTENT_LENGTH_ERROR_MESSAGE)
    private String content;

    List<TagDto> tagList;

    public PostDto() {
         this.tagList = new ArrayList<>();
    }

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
        this.tagList = new ArrayList<>();
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

    public List<TagDto> getTagList() {
        return new ArrayList<>(tagList);
    }

    public void setTagList(List<TagDto> tagList) {
        if(tagList != null && tagList.isEmpty()){
            this.tagList = tagList;
        }
    }


}