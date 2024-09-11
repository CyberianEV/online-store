package org.store.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.store.api.CartDto;
import org.store.api.CartItemDto;
import org.store.core.entities.Order;
import org.store.core.entities.Product;
import org.store.core.integrations.CartServiceIntegration;
import org.store.core.repositories.OrderRepository;
import org.store.core.services.OrderService;
import org.store.core.services.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = OrderService.class)
public class OrderServiceTests {
    @Autowired
    private OrderService orderService;

    @MockBean
    private CartServiceIntegration cartServiceIntegration;

    @MockBean
    private ProductService productService;

    @MockBean
    private OrderRepository orderRepository;

    private static CartDto cartDto;
    private static Product breadProd;
    private static Product cheeseProd;
    private static Product cucumberProd;
    private static Order expectedOrder;

    @BeforeAll
    public static void init() {
        cartDto = new CartDto();
        cartDto.setItems(List.of(
                new CartItemDto(1L, "Bread", BigDecimal.valueOf(1.59), 1, BigDecimal.valueOf(1.59)),
                new CartItemDto(2L, "Cheese", BigDecimal.valueOf(4.60), 2, BigDecimal.valueOf(2.30)),
                new CartItemDto(3L, "Cucumbers", BigDecimal.valueOf(1.80), 6, BigDecimal.valueOf(0.30))
        ));
        cartDto.setTotalPrice(BigDecimal.valueOf(7.99));

        breadProd = new Product();
        breadProd.setPrice(BigDecimal.valueOf(1.59));
        cheeseProd = new Product();
        cheeseProd.setPrice(BigDecimal.valueOf(2.30));
        cucumberProd = new Product();
        cucumberProd.setPrice(BigDecimal.valueOf(0.30));

        expectedOrder = new Order();
        expectedOrder.setUsername("User");
    }

    @Test
    public void shouldTrowExceptionWHenCartIsEmpty() {
        CartDto cartDto = new CartDto();
        cartDto.setItems(new ArrayList<>());

        Mockito.doReturn(cartDto)
                .when(cartServiceIntegration)
                .getCurrentCart("User");

        assertThrows(IllegalStateException.class, () -> orderService.createNewOrder("User"));
    }

    @Test
    public void CreateNewOrderTest() {

        expectedOrder.setTotalPrice(BigDecimal.valueOf(7.99));

        Mockito.doReturn(cartDto)
                .when(cartServiceIntegration)
                .getCurrentCart("User");

        Mockito.doReturn(Optional.of(breadProd))
                .when(productService)
                .findById(1L);

        Mockito.doReturn(Optional.of(cheeseProd))
                .when(productService)
                .findById(2L);

        Mockito.doReturn(Optional.of(cucumberProd))
                .when(productService)
                .findById(3L);

        orderService.createNewOrder("User");

        Mockito.verify(orderRepository, Mockito.times(1)).save(ArgumentMatchers.refEq(expectedOrder, "id", "orderItems", "createdAt", "updatedAt"));
    }

    @Test
    public void CreateNewOrderWithMissingProductTest() {

        expectedOrder.setTotalPrice(BigDecimal.valueOf(3.39));

        Mockito.doReturn(cartDto)
                .when(cartServiceIntegration)
                .getCurrentCart("User");

        Mockito.doReturn(Optional.of(breadProd))
                .when(productService)
                .findById(1L);

//        The absence of the product with ID 2 in the database is simulated,
//        accordingly the total price will consist of products 1 and 3, and will be equal to 3.39
        Mockito.doReturn(Optional.empty())
                .when(productService)
                .findById(2L);

        Mockito.doReturn(Optional.of(cucumberProd))
                .when(productService)
                .findById(3L);

        orderService.createNewOrder("User");

        Mockito.verify(orderRepository, Mockito.times(1)).save(ArgumentMatchers.refEq(expectedOrder, "id", "orderItems", "createdAt", "updatedAt"));
    }
}
