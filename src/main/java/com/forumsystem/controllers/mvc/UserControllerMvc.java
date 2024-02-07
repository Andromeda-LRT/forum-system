package com.forumsystem.controllers.mvc;

import com.forumsystem.modelhelpers.AuthenticationHelper;
import com.forumsystem.modelhelpers.UserModelFilterOptions;
import com.forumsystem.modelmappers.UserMapper;
import com.forumsystem.models.Post;
import com.forumsystem.models.User;
import com.forumsystem.models.modeldto.UserDto;
import com.forumsystem.services.contracts.UserService;
import com.forumsystem.еxceptions.AuthenticationFailureException;
import com.forumsystem.еxceptions.DuplicateEntityException;
import com.forumsystem.еxceptions.EntityNotFoundException;
import com.forumsystem.еxceptions.UnauthorizedOperationException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserControllerMvc {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public UserControllerMvc(UserService userService, UserMapper userMapper, AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public String showAllUsers(Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            List<User> users = userService.getAll(user, new UserModelFilterOptions(null, null, null, null, null));
            model.addAttribute("users", users);
            return "UsersView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("/{id}")
    public String showUserProfile(@PathVariable int id,
                                  Model model,
                                  HttpSession session) {
        try {
            User user = authenticationHelper.tryGetUser(session);
            model.addAttribute("user", userService.get(id, user));
            return "UserProfileView";
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable int id,
                             @Valid @ModelAttribute("user") UserDto userDto,
                             BindingResult bindingResult,
                             HttpSession session,
                             Model model) {
        User loggedUser;
        try {
            loggedUser = authenticationHelper.tryGetUser(session);
            authenticationHelper.verifyUserAccess(id, loggedUser);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }

        if (bindingResult.hasErrors()) {
            return "UserProfileView";
        }

        try {
            User userToUpdate = userMapper.fromDto(userDto);
            userService.update(userToUpdate, loggedUser);
            return "UserProfileView";
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (DuplicateEntityException e) {
            model.addAttribute("statusCode", HttpStatus.CONFLICT.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "UserProfileView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @PostMapping("/{id}/admin-rights")
    public String giveUserAdminRights(@PathVariable int id,
                                      BindingResult bindingResult,
                                      HttpSession session,
                                      Model model) {
        User loggedUser;
        try {
            loggedUser = authenticationHelper.tryGetUser(session);
            authenticationHelper.verifyUserAccess(id, loggedUser);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }

        try {
            User user = userService.get(id, loggedUser);
            userService.giveUserAdminRights(user, loggedUser);
            return "AllAdminsView"; // TODO To be discussed
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }


    @GetMapping("/count")
    public String showCountUsers(Model model) {
        long count = userService.getCountUsers();
        model.addAttribute("userCount", count);
        return "HomePageView";
    }

    @GetMapping("/{username}/posts")
    public String showUserPosts(@PathVariable String username,
                                HttpSession session,
                                Model model) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            List<Post> userPosts = userService.getUserPosts(username);
            model.addAttribute("userPosts", userPosts);
            return "UserPostsView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }
        try {
            userService.delete(id, user);
            return "AllUsersView"; //TODO Discuss about successfully delete page - LYUBIMA
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @PostMapping("/{id}/block")
    public String block(@PathVariable int id,
                        HttpSession session,
                        Model model) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            userService.blockUser(id, user);
            return "AllUsersView"; //TODO Discuss about successfully blocked page - LYUBIMA
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (DuplicateEntityException e) {
            model.addAttribute("statusCode", HttpStatus.CONFLICT.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "UserProfileView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @PostMapping("/{id}/unblock")
    public String unblock(@PathVariable int id,
                        HttpSession session,
                        Model model) {
        User user;
        try {
            user = authenticationHelper.tryGetUser(session);
        } catch (AuthenticationFailureException e) {
            return "redirect:/auth/login";
        }

        try {
            userService.unblockUser(id, user);
            return "AllUsersView"; //TODO Discuss about successfully blocked page - LYUBIMA
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        } catch (DuplicateEntityException e) {
            model.addAttribute("statusCode", HttpStatus.CONFLICT.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "UserProfileView";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }
}
