package com.forumsystem.controllers.mvc;

import com.forumsystem.modelmappers.PostResponseMapper;
import com.forumsystem.models.Post;
import com.forumsystem.models.modeldto.PostResponseDto;
import com.forumsystem.services.contracts.PostService;
import com.forumsystem.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeControllerMvc {
    private final UserService userService;
    private final PostService postService;
    private final PostResponseMapper postResponseMapper;

    @Autowired
    public HomeControllerMvc(UserService userService, PostService postService, PostResponseMapper postResponseMapper) {
        this.userService = userService;
        this.postService = postService;
        this.postResponseMapper = postResponseMapper;
    }

    @GetMapping
    public String showHomePage(Model model) {
        // Get post count
        long postCount = postService.getPostCount();

        // Get user count
        long userCount = userService.getCountUsers();

        //Top Ten Commented
        List<Post> postList = postService.getTopTenCommentedPosts();
        List<PostResponseDto> responsePostCommentList = postResponseMapper.convertToDTO(postList);

        //Newest Posts
        List<Post> postNewList = postService.getTenNewestPosts();
        List<PostResponseDto> responsePostNewList = postResponseMapper.convertToDTO(postNewList);

        model.addAttribute("postCount", postCount);
        model.addAttribute("userCount", userCount);
        model.addAttribute("tenNewestPosts", responsePostNewList);
        model.addAttribute("postCommentList", responsePostCommentList);

        return "HomePageView";
    }
}
