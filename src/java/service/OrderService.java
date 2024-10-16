/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import model.Order;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author cekesa
 */
public interface OrderService {

    void addOrder(Order order) throws SQLException;

    List<Order> getAllOrders() throws SQLException;

    List<Order> getOrdersByUserId(String userId) throws SQLException;

    void deleteOrder(Long orderId) throws SQLException;
}
