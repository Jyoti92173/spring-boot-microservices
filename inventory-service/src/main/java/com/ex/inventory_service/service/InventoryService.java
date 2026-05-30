package com.ex.inventory_service.service;

import com.ex.inventory_service.dto.InventoryResponse;
import com.ex.inventory_service.model.Inventory;
import com.ex.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public InventoryResponse findBySkuCode(String skuCode) {
        Inventory inventory = inventoryRepository
                .findBySkuCode(skuCode)
                .orElseThrow(() -> new RuntimeException(
                        "SKU not found: " + skuCode
                ));
        return InventoryResponse.builder()
                .skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantity() > 0)
                .quantity(inventory.getQuantity())
                .build();
    }
    // ✅ Check multiple items at once (order-service needs this!)
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        return inventoryRepository.findBySkuCodeIn(skuCodes)
                .stream()
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0)
                        .quantity(inventory.getQuantity())
                        .build())
                .toList();
    }
    // ✅ Add new inventory item
    @Transactional
    public void addInventory(String skuCode, int quantity) {
        Inventory inventory = new Inventory();
        inventory.setSkuCode(skuCode);
        inventory.setQuantity(quantity);
        inventoryRepository.save(inventory);
    }
    // ✅ Update quantity
    @Transactional
    public void updateQuantity(String skuCode, int quantity) {
        Inventory inventory = inventoryRepository
                .findBySkuCode(skuCode)
                .orElseThrow(() -> new RuntimeException(
                        "SKU not found: " + skuCode
                ));
        inventory.setQuantity(quantity);
        inventoryRepository.save(inventory);
    }
    // ✅ Reduce quantity when order is placed
    @Transactional
    public void reduceQuantity(String skuCode, int quantity) {
        Inventory inventory = inventoryRepository
                .findBySkuCode(skuCode)
                .orElseThrow(() -> new RuntimeException(
                        "SKU not found: " + skuCode
                ));

        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock!");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
    }



}