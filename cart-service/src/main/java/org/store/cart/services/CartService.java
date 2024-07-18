package org.store.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.api.ProductDto;
import org.store.cart.integrations.ProductServiceIntegration;
import org.store.cart.utils.Cart;
import javax.annotation.PostConstruct;
import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productService;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
        cart.setItems(new ArrayList<>());
    }

    public void addProductToCart(Long id) {
        ProductDto p = productService.findById(id);
        cart.addProduct(p);
    }

    public Cart getCurrentCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }

    public void changeCartItemQuantity(Long productId, int delta) {
        cart.changeItemQuantity(productId, delta);
    }
}
