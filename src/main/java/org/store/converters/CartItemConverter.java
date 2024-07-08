package org.store.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.store.dtos.CartItemDto;
import org.store.utils.CartItem;

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
