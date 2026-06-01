package com.ex.order_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "t_order_line_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String skuCode;             // unique product identifier

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;           // price at time of order (snapshot)

    @Column(nullable = false)
    private Integer quantity;           // units ordered

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;      // price × quantity


}
