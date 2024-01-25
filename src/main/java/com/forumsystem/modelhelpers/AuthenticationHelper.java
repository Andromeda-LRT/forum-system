package com.forumsystem.modelhelpers;

import com.forumsystem.models.User;
import com.forumsystem.services.UserService;
import com.forumsystem.еxceptions.EntityNotFoundException;
import com.forumsystem.еxceptions.UnauthorizedOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static com.forumsystem.modelhelpers.ModelConstantHelper.*;

@Component
public class AuthenticationHelper {

    private final UserService service;

    @Autowired
    public AuthenticationHelper(UserService service) {
        this.service = service;
    }

    public User tryGetUser(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    THE_REQUEST_RESOURCE_REQUIRES_AUTHENTICATION);
        }
        try {
            String authorizationHeader = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            String username = getUsername(authorizationHeader);
            String password = getPassword(authorizationHeader);

            User user = service.getUserByUsername(username);
            if (!user.getPassword().equals(password)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, INVALID_AUTHENTICATION);
            }

            return user;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, INVALID_AUTHENTICATION);
        }
    }

    private String getUsername(String authorizationHeader) {
        int firstSpaceIndex = authorizationHeader.indexOf(" ");
        if (firstSpaceIndex == -1) {
            throw new UnauthorizedOperationException(INVALID_AUTHENTICATION);
        }
        return authorizationHeader.substring(0, firstSpaceIndex);
    }

    private String getPassword(String authorizationHeader) {
        int firstSpaceIndex = authorizationHeader.indexOf(" ");
        if (firstSpaceIndex == -1) {
            throw new UnauthorizedOperationException(INVALID_AUTHENTICATION);
        }
        return authorizationHeader.substring(firstSpaceIndex + 1);
    }
}
