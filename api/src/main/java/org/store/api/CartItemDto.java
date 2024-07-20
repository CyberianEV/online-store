package org.store.api;

import java.math.BigDecimal;

public class CartItemDto {
    private Long productId;
    private String productTitle;
    private BigDecimal price;
    private int quantity;
    private BigDecimal pricePerProduct;

    public CartItemDto() {
    }

    public CartItemDto(Long productId, String productTitle, BigDecimal price, int quantity, BigDecimal pricePerProduct) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.price = price;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }
}
