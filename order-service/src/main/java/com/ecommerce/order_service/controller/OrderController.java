package com.ex.order_service.controller;

import com.ex.order_service.dto.OrderRequest;
import com.ex.order_service.dto.OrderResponse;
import com.ex.order_service.model.OrderStatus;
import com.ex.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping

        public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest orderRequest){
        OrderResponse response = orderService.placeOrder(orderRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)                   // 201
                .body(response);
    }

    // GET /api/order/{id} — get order by id
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id) {

        OrderResponse response = orderService.getOrderById(id);

        return ResponseEntity
                .status(HttpStatus.OK)                        // 200
                .body(response);
    }
    // GET /api/order/number/{orderNumber} — get order by order number
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<OrderResponse> getOrderByNumber(
            @PathVariable String orderNumber) {

        OrderResponse response = orderService.getOrderByNumber(orderNumber);

        return ResponseEntity
                .status(HttpStatus.OK)                        // 200
                .body(response);
    }
    // GET /api/order/customer/{customerId} — get all orders by customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomer(
            @PathVariable Long customerId) {

        List<OrderResponse> responses = orderService.getOrdersByCustomerId(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)                        // 200
                .body(responses);
    }
    // PUT /api/order/{id}/status — update order status
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {

        OrderResponse response = orderService.updateOrderStatus(id, status);

        return ResponseEntity
                .status(HttpStatus.OK)                        // 200
                .body(response);
    }
    // DELETE /api/order/{id} — cancel order
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelOrder(
            @PathVariable Long id) {

        orderService.cancelOrder(id);

        return ResponseEntity
                .status(HttpStatus.OK)                        // 200
                .body("Order cancelled successfully");
    }


}
