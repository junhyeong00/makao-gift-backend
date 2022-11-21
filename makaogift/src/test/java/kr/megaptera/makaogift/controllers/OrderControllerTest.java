package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.models.Order;
import kr.megaptera.makaogift.models.User;
import kr.megaptera.makaogift.services.OrderService;
import kr.megaptera.makaogift.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    @Test
    void orders() throws Exception {
        String userName = "test123";
        String name = "김토끼";
        User user = new User(1L, userName, name, 10000L);

        List<Order> orders = List.of(
                new Order(1L, name, "제조사 1", "상품 1", 2, 3000L, "산토끼", "산", "안녕",
                        LocalDateTime.of(2022, 11, 20, 11, 12, 10, 0)),
                new Order(2L, name, "제조사 2", "상품 2", 2, 3000L, "산토끼", "산", "안녕",
                        LocalDateTime.of(2022, 11, 20, 11, 12, 10, 0)),
                new Order(3L, name, "제조사 3", "상품 3", 2, 3000L, "산토끼", "산", "안녕",
                        LocalDateTime.of(2022, 11, 20, 11, 12, 10, 0))
        );
        int page = 1;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Page<Order> pageableOrders
                = new PageImpl<>(orders, pageable, orders.size());
        given(orderService.findOrdersByUserName(any(), any()))
                .willReturn(pageableOrders);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                        .header("Authorization", "Bearer " + token)
                        .param("page", "1"))
                .andExpect(status().isOk());
    }
}
