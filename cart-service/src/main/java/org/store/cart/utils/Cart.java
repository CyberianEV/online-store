package org.store.cart.utils;

import lombok.Data;
import org.store.api.ProductDto;
import org.store.cart.exceptions.ResourceNotFoundException;
import org.store.cart.exceptions.ZeroQuantityException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
        this.totalPrice = BigDecimal.ZERO;
    }

    public void addProduct(ProductDto p) {
        for (CartItem item : items) {
            if (item.getProductId().equals(p.getId())) {
                item.changeQuantity(1);
                recalculate();
                return;
            }
        }
        items.add(new CartItem(p.getId(), p.getTitle(), p.getPrice(), 1, p.getPrice()));
        recalculate();
    }

    public void changeItemQuantity(Long productId, int delta) {
        try {
            items.stream()
                    .filter(i -> i.getProductId().equals(productId))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("There is no such product in the cart, product_id:" + productId))
                    .changeQuantity(delta);
        } catch (ZeroQuantityException e) {
            items.removeIf(i -> i.getProductId().equals(productId));
        }
        recalculate();
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    public void merge(Cart anotherCart) {
        List<CartItem> anotherCartItems = anotherCart.getItems();
        Iterator<CartItem> iterator = anotherCartItems.iterator();
        while (iterator.hasNext()) {
            CartItem i = iterator.next();
            for (CartItem thisItem : this.items) {
                if (i.getProductId().equals(thisItem.getProductId())) {
                    thisItem.setQuantity(i.getQuantity() + thisItem.getQuantity());
                    thisItem.setPrice(i.getPrice().add(thisItem.getPrice()));
                    iterator.remove();
                }
            }
        }
        this.items.addAll(anotherCartItems);
        recalculate();
    }

    private void recalculate() {
        totalPrice = items.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
