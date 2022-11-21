package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.dtos.UserAmountDto;
import kr.megaptera.makaogift.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("amount")
    public UserAmountDto amount(
            @RequestAttribute("userName") String userName
    ) {
        Long amount = userService.amount(userName);

        return new UserAmountDto(amount);
    }
}
