package org.store.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.store.dtos.ProductDto;
import org.store.entities.Product;
import org.store.exceptions.ResourceNotFoundException;
import org.store.services.CategoryService;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    public Product dtoToEntity(ProductDto productDto) {
        Product p = new Product();
        p.setId(productDto.getId());
        p.setTitle(productDto.getTitle());
        p.setPrice(productDto.getPrice());
        p.setCategory(categoryService.findByTitle(productDto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product processing error, non-existent category: " + productDto.getCategory()
                ))
        );
        return p;
    }

    public ProductDto entityToDto(Product p) {
        ProductDto productDto = new ProductDto();
        productDto.setId(p.getId());
        productDto.setTitle(p.getTitle());
        productDto.setPrice(p.getPrice());
        productDto.setCategory(p.getCategory().getTitle());
        return productDto;
    }
}
