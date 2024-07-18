package org.store.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.store.core.dtos.CartItemDto;
import org.store.core.utils.CartItem;

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
