package org.store.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.store.api.CartDto;
import org.store.api.CartItemDto;
import org.store.core.entities.Order;
import org.store.core.entities.OrderItem;
import org.store.core.entities.Product;
import org.store.core.integrations.CartServiceIntegration;
import org.store.core.repositories.OrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductService productService;

    public List<Order> findByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }

    @Transactional
    public void createNewOrder(String username) {
        CartDto cart = cartServiceIntegration.getCurrentCart(username);
        List<CartItemDto> items = cart.getItems();
        if (items.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }
        Order order = new Order();
        order.setUsername(username);
        fetchOrderItemsAndCalculateTotalPrice(items, order);
        cartServiceIntegration.clearCart(username);
        orderRepository.save(order);
    }

    private void fetchOrderItemsAndCalculateTotalPrice(List<CartItemDto> items, Order order) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        order.setOrderItems(new ArrayList<>());

        for (CartItemDto i : items) {
            try {
                Product product = productService.findById(i.getProductId()).get();
                BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(i.getQuantity()));
                totalPrice = totalPrice.add(price);
                order.getOrderItems().add(createOrderItem(i, product, order, price));
            } catch (NoSuchElementException e) {
                continue;
            }
        }

        order.setTotalPrice(totalPrice);
    }

    private OrderItem createOrderItem(CartItemDto cartItem, Product product, Order order, BigDecimal price) {
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setOrder(order);
        item.setQuantity(cartItem.getQuantity());
        item.setPricePerProduct(product.getPrice());
        item.setPrice(price);
        return item;
    }
}
