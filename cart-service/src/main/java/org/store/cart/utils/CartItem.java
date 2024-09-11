package org.store.cart.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.store.cart.exceptions.ZeroQuantityException;

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

    public void changeQuantity(int delta) {
        quantity += delta;
        if (quantity <= 0) {
            throw new ZeroQuantityException("Cart item quantity is 0");
        }
        price = pricePerProduct.multiply(BigDecimal.valueOf(quantity));
    }
}
