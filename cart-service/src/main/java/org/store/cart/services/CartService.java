package org.store.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.store.api.ProductDto;
import org.store.cart.integrations.ProductServiceIntegration;
import org.store.cart.utils.Cart;

import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productService;
    private final RedisTemplate<String, Object> redisTemplate;

    public void addProductToCart(String cartId, Long id) {
        execute(cartId, cart -> {
            ProductDto p = productService.findById(id);
            cart.addProduct(p);
        });
    }

    public Cart getCurrentCart(String cartId) {
        if (!redisTemplate.hasKey(cartId)) {
            Cart currentCart = new Cart();
            redisTemplate.opsForValue().set(cartId, currentCart);
        }
        return (Cart)redisTemplate.opsForValue().get(cartId);
    }

    public void clearCart(String cartId) {
        execute(cartId, Cart::clear);
    }

    public void changeCartItemQuantity(String cartId, Long productId, int delta) {
        execute(cartId, cart -> cart.changeItemQuantity(productId, delta));
    }

    public void mergeGuestAndUserCarts(String sourceCartId, String destinationCartId) {
        if (!redisTemplate.hasKey(sourceCartId) || getCurrentCart(sourceCartId).getItems().isEmpty()) {
            redisTemplate.delete(sourceCartId);
            return;
        }
        Cart destinationCart = getCurrentCart(destinationCartId);
        if (destinationCart.getItems().isEmpty()) {
            redisTemplate.opsForValue().set(destinationCartId, getCurrentCart(sourceCartId));
            redisTemplate.delete(sourceCartId);
            return;
        }
        destinationCart.merge(getCurrentCart(sourceCartId));
        redisTemplate.opsForValue().set(destinationCartId, destinationCart);
        redisTemplate.delete(sourceCartId);
    }

    private void execute(String cartId, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartId);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartId, cart);
    }
}
