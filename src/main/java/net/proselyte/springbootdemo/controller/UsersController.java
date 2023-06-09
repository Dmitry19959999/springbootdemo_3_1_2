package net.proselyte.springbootdemo.controller;

import net.proselyte.springbootdemo.model.User;
import net.proselyte.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    @Autowired
    protected UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getUsers(Model model) {
        List<User> usersList = userService.getAllUsers();
        model.addAttribute("users", usersList);
        return "users";
    }

    @GetMapping("search")
    public String printUser(@RequestParam(value = "id", required = false, defaultValue = "0") long id,
                            Model model) {
        model.addAttribute("users", (userService.getUser(id)));
        return "users";
    }

    @GetMapping("/new")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "new_user";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("edit/{id}")
    public String editUserForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "edit_user";
    }

    @PutMapping("edit")
    public String updateUserInfo(@ModelAttribute("user") User user) {
        System.out.println(user);
        userService.updateUser(user);
        return "redirect:/users";
    }

    @DeleteMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}