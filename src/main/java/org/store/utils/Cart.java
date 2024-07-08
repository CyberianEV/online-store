package org.store.utils;

import lombok.Data;
import org.store.entities.Product;
import java.math.BigDecimal;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public void addProduct(Product p) {
        for (CartItem item : items) {
            if (item.getProductId().equals(p.getId())) {
                item.incrementQuantity();
                recalculate();
                return;
            }
        }
        items.add(new CartItem(p.getId(), p.getTitle(), p.getPrice(), 1, p.getPrice()));
        recalculate();
    }

    private void recalculate() {
        totalPrice = items.stream()
                .map(CartItem :: getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

//        totalPrice = BigDecimal.ZERO;
//        items.forEach(item -> totalPrice = totalPrice.add(item.getPrice()));
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }
}
