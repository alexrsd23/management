package com.rosendo.company.Entity.General;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String name;
    private String description;
    private Integer amount;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    private LocalDate register;
    private String barcode;
    private String brand;
    private BigDecimal netWeight;
    private BigDecimal grossWeight;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private Boolean changePrice;
    private Integer currentInventory;
    private Integer stockLimit;
    private String unit;
    private Boolean fractionalSale;
    private Boolean sellPromotion;
    private LocalDate promotionStartDate;
    private LocalDate endDatePromotion;
    private Double promotionalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public LocalDate getRegister() {
        return register;
    }

    public void setRegister(LocalDate register) {
        this.register = register;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Boolean getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(Boolean changePrice) {
        this.changePrice = changePrice;
    }

    public Integer getCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(Integer currentInventory) {
        this.currentInventory = currentInventory;
    }

    public Integer getStockLimit() {
        return stockLimit;
    }

    public void setStockLimit(Integer stockLimit) {
        this.stockLimit = stockLimit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getFractionalSale() {
        return fractionalSale;
    }

    public void setFractionalSale(Boolean fractionalSale) {
        this.fractionalSale = fractionalSale;
    }

    public Boolean getSellPromotion() {
        return sellPromotion;
    }

    public void setSellPromotion(Boolean sellPromotion) {
        this.sellPromotion = sellPromotion;
    }

    public LocalDate getPromotionStartDate() {
        return promotionStartDate;
    }

    public void setPromotionStartDate(LocalDate promotionStartDate) {
        this.promotionStartDate = promotionStartDate;
    }

    public LocalDate getEndDatePromotion() {
        return endDatePromotion;
    }

    public void setEndDatePromotion(LocalDate endDatePromotion) {
        this.endDatePromotion = endDatePromotion;
    }

    public Double getPromotionalPrice() {
        return promotionalPrice;
    }

    public void setPromotionalPrice(Double promotionalPrice) {
        this.promotionalPrice = promotionalPrice;
    }

    public Product() {
    }

    public Product(Long id, String image, String name, String description, Integer amount, Category category, Supplier supplier, LocalDate register, String barcode, String brand, BigDecimal netWeight, BigDecimal grossWeight, BigDecimal salePrice, BigDecimal costPrice, Boolean changePrice, Integer currentInventory, Integer stockLimit, String unit, Boolean fractionalSale, Boolean sellPromotion, LocalDate promotionStartDate, LocalDate endDatePromotion, Double promotionalPrice) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.supplier = supplier;
        this.register = register;
        this.barcode = barcode;
        this.brand = brand;
        this.netWeight = netWeight;
        this.grossWeight = grossWeight;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.changePrice = changePrice;
        this.currentInventory = currentInventory;
        this.stockLimit = stockLimit;
        this.unit = unit;
        this.fractionalSale = fractionalSale;
        this.sellPromotion = sellPromotion;
        this.promotionStartDate = promotionStartDate;
        this.endDatePromotion = endDatePromotion;
        this.promotionalPrice = promotionalPrice;
    }
}
