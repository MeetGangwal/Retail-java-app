package com.retailpulse.model;
import javax.persistence.*;
import java.util.Date;

@Entity
public class SaleOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    private Double totalAmount;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    public SaleOrder() {}
    public SaleOrder(Customer customer, Double totalAmount, Date orderDate) {
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }

    public Long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
}
