package com.rosendo.company.Domain.General.ItemSales;

import com.rosendo.company.Entity.General.Product;
import com.rosendo.company.Entity.General.Sales;

import java.math.BigDecimal;

public class UpdateItemSalesDTO {
    private Product product;
    private Integer quantity;
    private BigDecimal total;
    private Sales sales;
    private BigDecimal discount;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
