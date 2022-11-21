package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.models.Order;
import kr.megaptera.makaogift.services.OrderService;
import kr.megaptera.makaogift.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ActiveProfiles("test")
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @SpyBean
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setUp() {
        String identification = "test123";
        token = jwtUtil.encode(identification);
    }

    @Test
    void order() throws Exception {
        Order order = new Order(
                1L, "sender 1", "maker 1", "product name 1", 3, 50000L,
                "recipient 1", "address 1", "message to send 1");
        given(orderService.createOrder(any(), any(), any(),any(), any(),any(),any()))
                .willReturn(order);

        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .header("Authorization","Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":\"1\"," +
                                "\"purchaseCount\":\"2\"," +
                                "\"purchasePrice\":\"20000\"," +
                                "\"receiver\":\"산토끼\"," +
                                "\"address\":\"뉴욕\"," +
                                "\"messageToSend\":\"good\"" +
                                "}"))
                .andExpect(status().isCreated());

        verify(orderService).createOrder(
                any(String.class), any(Long.class), any(Integer.class), any(Long.class),
                any(String.class), any(String.class), any(String.class)
        );
    }

    @Test
    void orderWithBlankName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .header("Authorization","Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":\"1\"," +
                                "\"purchaseCount\":\"2\"," +
                                "\"purchasePrice\":\"20000\"," +
                                "\"receiver\":\"\"," +
                                "\"address\":\"뉴욕\"," +
                                "\"messageToSend\":\"good\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void orderWithBlankAddress() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .header("Authorization","Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"productId\":\"1\"," +
                                "\"purchaseCount\":\"2\"," +
                                "\"purchasePrice\":\"20000\"," +
                                "\"receiver\":\"산토끼\"," +
                                "\"address\":\"\"," +
                                "\"messageToSend\":\"good\"" +
                                "}"))
                .andExpect(status().isBadRequest());
    }
}
