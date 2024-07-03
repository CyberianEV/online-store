package org.store.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Data
@NoArgsConstructor
public class Cart {
    private List<Product> cart;

    @PostConstruct
    public void init() {
        cart = new ArrayList<>();
    }

    public void addProduct(Product product) {
        cart.add(product);
    }

    public List<Product> getCart() {
        return Collections.unmodifiableList(cart);
    }
}
