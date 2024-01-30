package com.forumsystem.models;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class PostResponseDto {
    private String createdBy;
    private String title;

    private String content;

    private int likes;

    private int dislikes;

    private String createdAt;

    public PostResponseDto() {}

    public PostResponseDto(String createdBy, String title, String content, int likes, int dislikes, String createdAt) {
        this.createdBy = createdBy;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime dateTime) {
        this.createdAt = formatDateAndTime(dateTime);
    }

    private String formatDateAndTime(LocalDateTime dateTime) {
        ZoneId bulgariaZoneId = ZoneId.of("Europe/Sofia");
        ZonedDateTime postDateTime = dateTime.atZone(bulgariaZoneId);
        ZonedDateTime now = ZonedDateTime.now(bulgariaZoneId);

        long minutesDiff = ChronoUnit.MINUTES.between(postDateTime, now);
        long hoursDiff = ChronoUnit.HOURS.between(postDateTime, now);
        long daysDiff = ChronoUnit.DAYS.between(postDateTime, now);


        if (minutesDiff <= 1) {
            return "Just now";
        } else if (minutesDiff < 60) {
            return minutesDiff + " minutes ago";
        } else if (hoursDiff < 24) {
            return hoursDiff + " hours ago";
        } else if (daysDiff == 1) {
            return "Yesterday";
        } else if (daysDiff < 7) {
            return daysDiff + " days ago";
        } else {
            return dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH));
        }
    }
}
