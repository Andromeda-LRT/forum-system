package com.forumsystem.controllers.mvc;

import com.forumsystem.modelmappers.PostResponseMapper;
import com.forumsystem.models.Post;
import com.forumsystem.models.modeldto.PostResponseDto;
import com.forumsystem.services.contracts.PostService;
import com.forumsystem.services.contracts.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController("/home")
public class HomeControllerMvc {
    private final UserService userService;
    private final PostService postService;
    private final PostResponseMapper postResponseMapper;

    public HomeControllerMvc(UserService userService, PostService postService, PostResponseMapper postResponseMapper) {
        this.userService = userService;
        this.postService = postService;
        this.postResponseMapper = postResponseMapper;
    }

    @GetMapping("/userCount")
    public String showCountUsers(Model model) {
        long userCount = userService.getCountUsers();
        model.addAttribute("userCount", userCount);
        return "HomePageView";
    }

    @GetMapping("/postCount")
    public String showPostCount(Model model){
        long postCount = postService.getPostCount();
        model.addAttribute("postCount", postCount);
        return "HomePageView";
    }

    @GetMapping("/mostCommentedPosts")
    public String showTopTenCommentedPosts(Model model){
        List<Post> postList = postService.getTopTenCommentedPosts();
        List<PostResponseDto> responsePostList = postResponseMapper.convertToDTO(postList);
        model.addAttribute("topTenCommentedPosts", responsePostList);
        return "HomePageView";
    }

    @GetMapping("/tenNewestPosts")
    public String showTenNewestPosts(Model model){
        List<Post> postList = postService.getTenNewestPosts();
        List<PostResponseDto> responsePostList = postResponseMapper.convertToDTO(postList);
        model.addAttribute("tenNewestPosts", responsePostList);
        return "HomePageView";
    }
}
