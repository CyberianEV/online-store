package org.store.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.store.core.dtos.ProductDto;
import org.store.core.entities.Product;
import org.store.core.exceptions.ResourceNotFoundException;
import org.store.core.services.CategoryService;

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

    public org.store.core.soap.products.Product entityToSoap(Product p) {
        org.store.core.soap.products.Product soapProduct = new org.store.core.soap.products.Product();
        soapProduct.setId(p.getId());
        soapProduct.setTitle(p.getTitle());
        soapProduct.setPrice(p.getPrice());
        soapProduct.setCategory(p.getCategory().getTitle());
        return soapProduct;
    }
}
