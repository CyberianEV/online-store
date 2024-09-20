package org.store.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.store.core.converters.ProductConverter;
import org.store.api.ProductDto;
import org.store.core.entities.Product;
import org.store.core.exceptions.ResourceNotFoundException;
import org.store.core.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;

    @GetMapping
    public Page<ProductDto> findAll(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "page_size", defaultValue = "5") Integer pageSize
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.findAll(page - 1, pageSize)
                .map(productConverter::entityToDto);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return productConverter.entityToDto(productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find product, id: " + id)));
    }

    @PostMapping
    public ProductDto createNewProduct(@RequestBody ProductDto productDto) {
        Product product = productConverter.dtoToEntity(productDto);
        return productConverter.entityToDto(productService.createNewProduct(product));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
