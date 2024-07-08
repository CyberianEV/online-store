package org.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.exceptions.ResourceNotFoundException;
import org.store.utils.Cart;
import org.store.entities.Product;
import javax.annotation.PostConstruct;
import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private Cart cart;

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

    @PostConstruct
    public void init() {
        cart = new Cart();
        cart.setItems(new ArrayList<>());
    }
}
