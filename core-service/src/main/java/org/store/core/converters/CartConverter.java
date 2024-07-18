package org.store.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.store.core.dtos.CartDto;
import org.store.core.dtos.CartItemDto;
import org.store.core.utils.Cart;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartDto entityToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        List<CartItemDto> itemsDto = new ArrayList<>();
        cart.getItems().forEach(i -> itemsDto.add(cartItemConverter.entityToDto(i)));
        cartDto.setItems(itemsDto);
        cartDto.setTotalPrice(cart.getTotalPrice());
        return cartDto;
    }
}
