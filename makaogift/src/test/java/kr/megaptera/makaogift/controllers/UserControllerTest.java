package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.exceptions.RegisterFailed;
import kr.megaptera.makaogift.models.User;
import kr.megaptera.makaogift.services.UserService;
import kr.megaptera.makaogift.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @SpyBean
    private JwtUtil jwtUtil;

    @SpyBean
    private PasswordEncoder passwordEncoder;

    private String token;
    private String userName;
    private String name;
    private String password;
    private User user;

    @BeforeEach
    void setUp() {
        userName = "jhbae0420";
        name = "배준형";
        password = "Password1234!";
        user = new User(1L, name, userName, 100L);
        user.changePassword(password, passwordEncoder);
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

    @Test
    void register() throws Exception {
        given(userService.createUser(name, userName, password, password))
                .willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"" +
                                "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void registerWithBlankName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithIncorrectName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"hi\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithBlankUserName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithIncorrectUserName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"test-123\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithBlankPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"\"," +
                                "\"confirmPassword\":\"Password1234!\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithIncorrectPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"test\"," +
                                "\"confirmPassword\":\"test\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithBlankConfirmPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerWithAlreadyExistingUserName() throws Exception {
        given(userService.createUser(name, userName, password, password))
                .willThrow(new RegisterFailed("해당 아이디는 사용할 수 없습니다"));

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\":\"배준형\"," +
                                "\"userName\":\"jhbae0420\"," +
                                "\"password\":\"Password1234!\"," +
                                "\"confirmPassword\":\"Password1234!\"" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString("1007")
                ));
    }
}
