package com.ex.inventory_service.controller;

import com.ex.inventory_service.dto.InventoryResponse;
import com.ex.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // ✅ Add inventory
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addInventory(@RequestParam String skuCode,
                               @RequestParam int quantity) {
        inventoryService.addInventory(skuCode, quantity);
        return "Inventory added successfully!";
    }

    // Check single SKU
    @GetMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse findBySkuCode(
            @PathVariable String skuCode) {

        return inventoryService.findBySkuCode(skuCode);
    }

    // ✅ Check multiple SKUs (called by order-service)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(
            @RequestParam List<String> skuCodes) {
        return inventoryService.isInStock(skuCodes);
    }



    // ✅ Reduce quantity
    @PutMapping("/reduce")
    @ResponseStatus(HttpStatus.OK)
    public String reduceQuantity(@RequestParam String skuCode,
                                 @RequestParam int quantity) {
        inventoryService.reduceQuantity(skuCode, quantity);
        return "Quantity reduced successfully!";
    }
}
