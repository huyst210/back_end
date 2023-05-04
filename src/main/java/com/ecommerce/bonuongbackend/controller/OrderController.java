package com.ecommerce.bonuongbackend.controller;

import com.ecommerce.bonuongbackend.dto.ResponseDto;
import com.ecommerce.bonuongbackend.dto.order.*;
import com.ecommerce.bonuongbackend.dto.user.ActivateDto;
import com.ecommerce.bonuongbackend.service.OrderService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("")
    public GetOrdersResponseDto getOrders() {
        return orderService.getOrders();
    }

    @PostMapping("")
    public CreateOrderResponseDto createOrder(@RequestBody CreateOrderDto createOrderDto) {
        return orderService.createOrder(createOrderDto);
    }

    @PutMapping("/{id}")
    public CreateOrderResponseDto updateOrder(@PathVariable String id, @RequestBody UpdateOrderDto updateOrderDto) {
        return orderService.updateOrder(id, updateOrderDto);
    }

    @GetMapping("/users/{userId}")
    public GetOrdersResponseDto getUserOrders(@PathVariable String userId) {
        return orderService.getUserOrders(userId);
    }

    // stripe session checkout api
    @PostMapping("/create-checkout-session")
    public PaymentResponseDto createCheckout(@RequestBody CreateOrderDto createOrderDto) throws StripeException {
        return orderService.createSession(createOrderDto);
    }

    @PutMapping("/update-checkout")
    public ResponseDto updateCheckout(@RequestBody ActivateDto updateCheckoutDto) {
        return orderService.updateCheckout(updateCheckoutDto);
    }
}
