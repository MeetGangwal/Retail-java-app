package com.retailpulse.model;
import javax.persistence.*;

@Entity
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "sale_order_id")
    private SaleOrder saleOrder;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    private Integer quantity;
    private Double price; 

    public OrderItem() {}
    public OrderItem(SaleOrder saleOrder, Product product, Integer quantity, Double price) {
        this.saleOrder = saleOrder;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() { return id; }
    public SaleOrder getSaleOrder() { return saleOrder; }
    public void setSaleOrder(SaleOrder saleOrder) { this.saleOrder = saleOrder; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
