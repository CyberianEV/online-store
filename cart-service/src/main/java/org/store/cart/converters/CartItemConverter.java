package org.store.cart.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.store.api.CartItemDto;
import org.store.cart.utils.CartItem;

@Component
@RequiredArgsConstructor
public class CartItemConverter {
    public CartItemDto entityToDto(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getProductId(),
                cartItem.getProductTitle(),
                cartItem.getPrice(),
                cartItem.getQuantity(),
                cartItem.getPricePerProduct()
        );
    }
}
