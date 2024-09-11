package org.store.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.store.api.OrderItemDto;
import org.store.core.entities.OrderItem;

@Component
@RequiredArgsConstructor
public class OrderItemConverter {
    public OrderItemDto entityToDto(OrderItem oi) {
        return new OrderItemDto(
                oi.getProduct().getId(),
                oi.getProduct().getTitle(),
                oi.getPrice(),
                oi.getQuantity(),
                oi.getPricePerProduct()
        );
    }
}
