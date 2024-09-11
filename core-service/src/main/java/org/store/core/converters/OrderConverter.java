package org.store.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.store.api.OrderDto;
import org.store.core.entities.Order;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public OrderDto entityToDto(Order o) {
        return new OrderDto(
                o.getId(),
                o.getOrderItems().stream().map(orderItemConverter::entityToDto).collect(Collectors.toList()),
                o.getTotalPrice(),
                o.getCreatedAt()
        );
    }
}