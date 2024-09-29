package org.store.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.store.api.PageDto;
import org.store.core.converters.PageConverter;
import org.store.core.converters.ProductConverter;
import org.store.api.ProductDto;
import org.store.core.entities.Product;
import org.store.core.exceptions.AppError;
import org.store.core.exceptions.ResourceNotFoundException;
import org.store.core.repositories.specifications.ProductSpecifications;
import org.store.core.services.ProductService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product handling methods")
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final PageConverter pageConverter;

    @GetMapping
    public PageDto<ProductDto> findAll(
            @RequestParam(name = "p", defaultValue = "1") @Parameter(description = "Page number", required = true) Integer page,
            @RequestParam(name = "page_size", defaultValue = "5") @Parameter(description = "Products on page", required = false) Integer pageSize,
            @RequestParam(name = "title_part", required = false) @Parameter(description = "Product name part filter", required = false) String titlePart,
            @RequestParam(name = "min_price", required = false) @Parameter(description = "Min product price filter") Integer minPrice,
            @RequestParam(name = "max_price", required = false) @Parameter(description = "Max product price filter") Integer maxPrice
    ) {
        if (page < 1) {
            page = 1;
        }
        Specification<Product> spec = Specification.where(null);
        if (titlePart != null) {
            spec = spec.and(ProductSpecifications.titleLike(titlePart));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(BigDecimal.valueOf(minPrice)));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.priceLessOrEqualsThan(BigDecimal.valueOf(maxPrice)));
        }

        return pageConverter.entityToDto(productService.findAll(page - 1, pageSize, spec)
                .map(productConverter::entityToDto));
    }

    @Operation(
            summary = "Request to receive a product by id",
            responses = {
                    @ApiResponse(
                            description = "Successful response", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Product not found", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable @Parameter(description = "Product ID", required = true) Long id) {
        return productConverter.entityToDto(productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find product, id: " + id)));
    }

    @Operation(
            summary = "Request to add a new product",
            responses = {
                    @ApiResponse(
                            description = "Product successfully created", responseCode = "201"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createNewProduct(@RequestBody ProductDto productDto) {
        Product product = productConverter.dtoToEntity(productDto);
        return productConverter.entityToDto(productService.createNewProduct(product));
    }

    @Operation(
            summary = "Request to delete a product by ID",
            responses = {
                    @ApiResponse(
                            description = "Product successfully deleted", responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @Parameter(description = "Product ID", required = true, example = "7") Long id) {
        productService.deleteById(id);
    }
}
