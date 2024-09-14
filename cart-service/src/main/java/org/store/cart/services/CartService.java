package org.store.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.api.ProductDto;
import org.store.cart.integrations.ProductServiceIntegration;
import org.store.cart.utils.Cart;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productService;
    private Map<String, Cart> carts;

    @PostConstruct
    public void init() {
        carts = new HashMap<>();
    }

    public void addProductToCart(String cartId, Long id) {
        ProductDto p = productService.findById(id);
        getCurrentCart(cartId).addProduct(p);
    }

    public Cart getCurrentCart(String cartId) {
        if (!carts.containsKey(cartId)) {
            Cart currentCart = new Cart();
            carts.put(cartId, currentCart);
        }
        return carts.get(cartId);
    }

    public void clearCart(String cartId) {
        getCurrentCart(cartId).clear();
    }

    public void changeCartItemQuantity(String cartId, Long productId, int delta) {
        getCurrentCart(cartId).changeItemQuantity(productId, delta);
    }

    public void mergeGuestAndUserCarts(String sourceCartId, String destinationCartId) {
        if (!carts.containsKey(sourceCartId) || getCurrentCart(sourceCartId).getItems().isEmpty()) {
            carts.remove(sourceCartId);
            return;
        }
        Cart destinationCart = getCurrentCart(destinationCartId);
        if (destinationCart.getItems().isEmpty()) {
            carts.put(destinationCartId, getCurrentCart(sourceCartId));
            carts.remove(sourceCartId);
            return;
        }
        destinationCart.merge(getCurrentCart(sourceCartId));
        carts.remove(sourceCartId);
    }
}
