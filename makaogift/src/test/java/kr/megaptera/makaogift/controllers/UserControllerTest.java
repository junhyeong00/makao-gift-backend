package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.services.UserService;
import kr.megaptera.makaogift.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String token;
    private String userName;

    @BeforeEach
    void setUp() {
        userName = "test123";
        token = jwtUtil.encode(userName);
    }

    @Test
    void amount() throws Exception {
        given(userService.amount(userName)).willReturn(50000L);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/amount")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("50000")
                ));

        verify(userService).amount(userName);
    }
}