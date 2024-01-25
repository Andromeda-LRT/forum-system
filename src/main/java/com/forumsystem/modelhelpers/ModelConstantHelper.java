package com.forumsystem.modelhelpers;

public class ModelConstantHelper {
    public static final String EMPTY_TITLE_ERROR_MESSAGE = "Title cannot be empty";
    public static final String INVALID_TITLE_LENGTH_ERROR_MESSAGE =
            "Title has to be between 16 and 64 characters";
    public static final String INVALID_CONTENT_LENGTH_ERROR_MESSAGE =
            "Content has to be between 32 and 8192 characters long";
    public static final String EMPTY_CONTENT_ERROR_MESSAGE = "Content cannot be empty";
    public static final String UNAUTHORIZED_DELETION_ERROR_MESSAGE =
            "You cannot remove another user's post";
    public static final String UNAUTHORIZED_EDIT_ERROR_MESSAGE =
            "You cannot modify another user's post";
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String THE_REQUEST_RESOURCE_REQUIRES_AUTHENTICATION =
            "The request resource requires authentication.";
    public static final String INVALID_AUTHENTICATION = "Invalid authentication";
}
