package org.store.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.core.exceptions.ResourceNotFoundException;
import org.store.core.utils.Cart;
import org.store.core.entities.Product;
import javax.annotation.PostConstruct;
import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
        cart.setItems(new ArrayList<>());
    }

    public void addProductToCart(Long id) {
        Product p = productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cannot add product to cart, product does not exist, id: " + id
                ));
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
