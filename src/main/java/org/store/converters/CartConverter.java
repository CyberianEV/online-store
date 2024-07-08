package org.store.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.store.dtos.CartDto;
import org.store.dtos.CartItemDto;
import org.store.utils.Cart;

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
