package org.store.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.core.entities.Product;
import org.store.core.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product createNewProduct(Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
