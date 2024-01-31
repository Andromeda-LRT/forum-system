package com.forumsystem.modelhelpers;

public class ModelConstantHelper {
    public static final String EMPTY_TITLE_ERROR_MESSAGE =
            "Title cannot be empty";
    public static final String INVALID_TITLE_LENGTH_ERROR_MESSAGE =
            "Title has to be between 16 and 64 characters";
    public static final String INVALID_CONTENT_LENGTH_ERROR_MESSAGE =
            "Content has to be between 32 and 8192 characters long";
    public static final String EMPTY_CONTENT_ERROR_MESSAGE =
            "Content cannot be empty";
    public static final String UNAUTHORIZED_DELETION_ERROR_MESSAGE =
            "You may remove only posts, which you have created";
    public static final String UNAUTHORIZED_EDIT_ERROR_MESSAGE =
            "You may edit only %s, which you have created";
    public static final String POSTS = "posts";
    public static final String COMMENTS = "comments";

    public static final String BLOCKED_USER_ERROR_MESSAGE =
            ("%s %s is restricted while blocked");
    public static final String POST = "Post";
    public static final String COMMENT = "Comment";
    public static final String MODIFICATION = "modification";
    public static final String CREATION = "creation";
    public static final String EDITING = "editing";
    public static final String AUTHORIZATION_HEADER_NAME =
            "Authorization";
    public static final String THE_REQUEST_RESOURCE_REQUIRES_AUTHENTICATION =
            "The requested resource requires authentication.";
    public static final String INVALID_AUTHENTICATION =
            "Invalid authentication";

    public static final String UNAUTHORIZED_TO_BROWSE_USER_INFORMATION =
            "You are not authorized to browse user information";
    public static final String PERMISSIONS_ERROR =
            "You don't have the permissions to update this user information.";
    public static final String INSUFFICIENT_PERMISSIONS_ERROR_MESSAGE =
            "You do not have the required permissions to review the requested resource";
    public static final String HASHTAG = "#";
}
