package org.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.store.converters.ProductConverter;
import org.store.dtos.ProductDto;
import org.store.services.CartService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/carts/service")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductConverter productConverter;

    @GetMapping
    public List<ProductDto> getAllProductsFromCart() {
        return cartService.getAllProducts().stream()
                .map(productConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping
    public void addProductToCart(@RequestBody ProductDto productDto) {
        cartService.addProductToCart(productConverter.dtoToEntity(productDto));
    }

}
