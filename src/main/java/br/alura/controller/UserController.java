package br.alura.controller;

import br.alura.dto.LoginDTO;
import br.alura.dto.TokenDTO;
import br.alura.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public void addUser() {
        userService.addUser();
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody final LoginDTO login) {
        return userService.login(login);
    }
}
