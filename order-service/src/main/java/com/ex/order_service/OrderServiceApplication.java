package com.ex.order_service;

import com.ex.order_service.dto.OrderLineItemsDto;
import com.ex.order_service.dto.OrderRequest;
import com.ex.order_service.model.Order;
import com.ex.order_service.model.OrderLineItems;
import com.ex.order_service.repo.OrderRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}





