package org.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.entities.Cart;
import org.store.entities.Product;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final Cart cart;

    public List<Product> getAllProducts() {
        return cart.getCart();
    }

    public void addProductToCart(Product product) {
        cart.addProduct(product);
    }

}
