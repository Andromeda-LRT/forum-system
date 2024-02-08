package com.forumsystem.controllers.mvc;

import com.forumsystem.services.contracts.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("/home")
public class HomeControllerMvc {
    private final UserService userService;

    public HomeControllerMvc(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/count")
    public String showCountUsers(Model model) {
        long count = userService.getCountUsers();
        model.addAttribute("userCount", count);
        return "HomePageView";
    }
}
