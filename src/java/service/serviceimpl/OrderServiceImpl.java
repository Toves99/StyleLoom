/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.serviceimpl;

import Utils.DatabaseConnect;
import Utils.Queries;
import Utils.Response;
import java.sql.SQLException;
import java.util.List;
import model.Order;
import service.OrderService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import model.Product;
import java.sql.ResultSet;

/**
 *
 * @author cekesa
 */
public class OrderServiceImpl implements OrderService {

    @Override
    public void addOrder(Order order) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.INSERT_ORDER)) {
            stmt.setLong(1, order.getUserId());
            stmt.setDouble(2, order.getTotalAmount());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println(Response.ADD_ORDER_SUCCESS_MESSAGE);
            } else {
                System.out.println(Response.ADD_ORDER_ERROR_MESSAGE);
            }
            // Insert products related to the order
            for (Product product : order.getProducts()) {
                try (PreparedStatement stmt1 = conn.prepareStatement(Queries.INSERT_ORDER_PRODUCT)) {
                    stmt1.setLong(1, order.getId());
                    stmt1.setLong(2, product.getId());
                    stmt1.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while adding the order: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.FETCH_ALL_ORDERS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setUserId(rs.getLong("userId"));
                order.setTotalAmount(rs.getDouble("totalAmount"));

                // Fetch products for each order
                List<Product> products = getProductsForOrder(order.getId(), conn);
                order.setProducts(products);

                orders.add(order);
            }

            System.out.println(Response.FETCH_ALL_ORDER_SUCCESS_MESSAGE);
        } catch (SQLException e) {
            System.out.println(Response.FETCH_ALL_ORDER_ERROR_MESSAGE);
            throw e;
        }

        return orders;
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) throws SQLException {
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.FETCH_ORDERS_BY_USER)) {
            
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getLong("id"));
                    order.setUserId(rs.getLong("userId"));
                    order.setTotalAmount(rs.getDouble("totalAmount"));

                    // Fetch products for each order
                    List<Product> products = getProductsForOrder(order.getId(), conn);
                    order.setProducts(products);

                    orders.add(order);
                }
            }

            System.out.println(Response.FETCH_ORDER_SUCCESS_MESSAGE);
        } catch (SQLException e) {
            System.out.println(Response.FETCH_ORDER_ERROR_MESSAGE);
            throw e;
        }

        return orders;
    }

    @Override
    public void deleteOrder(Long orderId) throws SQLException {
        try(Connection conn=DatabaseConnect.getConnection();
            PreparedStatement stmt=conn.prepareStatement(Queries.DELETE_ORDER_BY_ID)){
            stmt.setLong(1, orderId);
            int rowAffected=stmt.executeUpdate();
            if(rowAffected>0){
                System.out.println(Response.DELETE_ORDER_SUCCESS_MESSAGE);
            }else{
                System.out.println(Response.DELETE_ORDER_ERROR_MESSAGE);
            }
        }catch (SQLException e) {
            System.out.println(Response.DELETE_ORDER_ERROR_MESSAGE);
            throw e;
        }
    }
    
    
    
    
    
    // Helper method to fetch products for a specific order
    private List<Product> getProductsForOrder(Long orderId, Connection conn) throws SQLException {
        List<Product> products = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(Queries.FETCH_PRODUCTS_BY_ORDER)) {
            stmt.setLong(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong("productId"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setDescription(rs.getString("description"));

                    products.add(product);
                }
            }
        }

        return products;
    }

}
