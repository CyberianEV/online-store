package org.store.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.store.api.CartDto;
import org.store.api.StringResponse;
import org.store.cart.converters.CartConverter;
import org.store.cart.services.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/{guestCartId}")
    public CartDto getCurrentCart(@PathVariable String guestCartId, @RequestHeader(required = false) String username) {
        String currentCartId = selectCartId(guestCartId, username);
        return cartConverter.entityToDto(cartService.getCurrentCart(currentCartId));
    }

    @GetMapping("/{guestCartId}/add/{productId}")
    public void addProductToCart(@PathVariable String guestCartId, @RequestHeader(required = false) String username,
                                 @PathVariable Long productId) {
        String currentCartId = selectCartId(guestCartId, username);
        cartService.addProductToCart(currentCartId, productId);
    }

    @GetMapping("/{guestCartId}/clear")
    public void clearCart(@PathVariable String guestCartId, @RequestHeader(required = false) String username) {
        String currentCartId = selectCartId(guestCartId, username);
        cartService.clearCart(currentCartId);
    }

    @GetMapping("/{guestCartId}/change_quantity")
    public void changeCartItemQuantity(@PathVariable String guestCartId, @RequestHeader(required = false) String username,
                                       @RequestParam Long productId, @RequestParam int delta) {
        String currentCartId = selectCartId(guestCartId, username);
        cartService.changeCartItemQuantity(currentCartId, productId, delta);
    }

    @GetMapping("/generate_id")
    public StringResponse generateGuestCartId() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    private String selectCartId(String guestCartId, String username) {
        if (username != null) {
            return username;
        }
        return guestCartId;
    }

}
