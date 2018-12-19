package com.es.phoneshop.model.product;

import com.es.phoneshop.model.entity.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Product extends Entity implements Serializable {
    private String code;
    private String description;
    /**
     * null means there is no price because the product is outdated or new
     */
    private BigDecimal price;
    /**
     * can be null if the price is null
     */
    private Currency currency;
    private int stock;
    private String imageUrl;

    public Product() {
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        super(id);
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Product product = (Product) object;
        return this.getId().equals(product.getId())
                && code.equals(product.code)
                && description.equals(product.description)
                && price.equals(product.price)
                && currency.equals(product.currency)
                && stock == product.stock
                && imageUrl.equals(product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description, price, currency, stock, imageUrl);
    }
}