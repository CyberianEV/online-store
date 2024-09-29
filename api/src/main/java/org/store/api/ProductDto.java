package org.store.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Product model")
public class ProductDto {
    @Schema(description = "Product ID", required = true, example = "1")
    private Long id;

    @Schema(description = "Product name", required = true, example = "Bread")
    private String title;

    @Schema(description = "Product price", required = true, example = "1.99")
    private BigDecimal price;

    @Schema(description = "Product category", required = true, example = "Food")
    private String category;

    public ProductDto() {
    }

    public ProductDto(Long id, String title, BigDecimal price, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
