package com.ex.inventory_service.model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "t_inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @Column(unique = true)
    private String skuCode;

    private int quantity;

}
