package org.store.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long productId;
    private String productTitle;
    private BigDecimal price;
    private int quantity;
    private BigDecimal pricePerProduct;

    public void incrementQuantity() {
        quantity++;
        price = price.add(pricePerProduct);
    }
}
