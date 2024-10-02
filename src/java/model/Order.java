/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author cekesa
 */
public class Order {
    private Long id;
    private String userId;
    private List<Product> products;
    private double totalAmount;

    public Order() {
    }

    public Order(Long id, String userId, List<Product> products, double totalAmount) {
        this.id = id;
        this.userId = userId;
        this.products = products;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", userId=" + userId + ", products=" + products + ", totalAmount=" + totalAmount + '}';
    }
    
}
