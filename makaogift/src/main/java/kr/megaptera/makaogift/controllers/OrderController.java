package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.dtos.OrderCreateDto;
import kr.megaptera.makaogift.dtos.OrderDto;
import kr.megaptera.makaogift.dtos.OrderErrorDto;
import kr.megaptera.makaogift.dtos.OrderResultDto;
import kr.megaptera.makaogift.dtos.OrdersDto;
import kr.megaptera.makaogift.exceptions.OrderFailed;
import kr.megaptera.makaogift.models.Order;
import kr.megaptera.makaogift.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("orders")
    public OrdersDto orders(
            @RequestAttribute("userName") String userName,
            @RequestParam(required = false, defaultValue = "1") Integer page
    ) {
        Page<Order> orders = orderService.findOrdersByUserName(userName, page);

        int totalPageCount = orders.getTotalPages();

        List<OrderDto> orderDtos = orders.stream()
                .map(Order::toDto).toList();

        Page<OrderDto> pageableOrderDtos
                = new PageImpl<>(orderDtos, PageRequest.of(page -1, 8), orderDtos.size());
        return new OrdersDto(pageableOrderDtos, totalPageCount);
    }


    @PostMapping("order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResultDto order(
            @RequestAttribute("userName") String userName,
            @Validated @RequestBody OrderCreateDto orderCreateDto, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new OrderFailed(errorMessage);
        }

        Order order = orderService.createOrder(
                userName,
                orderCreateDto.getProductId(),
                orderCreateDto.getPurchaseCount(),
                orderCreateDto.getPurchasePrice(),
                orderCreateDto.getReceiver(),
                orderCreateDto.getAddress(),
                orderCreateDto.getMessageToSend()
        );

        return order.toOrderResultDto();
    }

    @GetMapping("orders/{id}")
    public OrderDto orderDetail(
            @RequestAttribute("userName") String userName,
            @PathVariable("id") Long orderId
    ) {
        Order order = orderService.orderDetail(orderId);
        return order.toDto();
    }

    @ExceptionHandler(OrderFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OrderErrorDto orderFailed(OrderFailed e) {
        return new OrderErrorDto(e.getErrorMessage());
    }
}
