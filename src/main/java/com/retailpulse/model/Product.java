package com.retailpulse.model;
import javax.persistence.*;

@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String unitCategory;
    private String name;
    private Double price;
    private Integer stock;

    public Product() {}
    public Product(String unitCategory, String name, Double price, Integer stock) {
        this.unitCategory = unitCategory;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() { return id; }
    public String getUnitCategory() { return unitCategory; }
    public void setUnitCategory(String unitCategory) { this.unitCategory = unitCategory; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
