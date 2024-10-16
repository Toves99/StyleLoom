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
import model.Product;
import service.ProductService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author cekesa
 */
public class ProductServiceImpl implements ProductService {

    @Override
    public void addProduct(Product product) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.INSERT_PRODUCT)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getDescription());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(Response.ADD_PRODUCT_SUCCESS_MESSAGE);
                System.out.println(Response.successCode);
            } else {
                System.out.println(Response.ADD_PRODUCT_ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred during adding product: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Product getProductById(Long id) throws SQLException {
        Product product = null;
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.SELECT_PRODUCT_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                product = new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getLong("userId")
                );
                System.out.println(Response.FETCH_PRODUCT_SUCCESS_MESSAGE);
            } else {
                System.out.println(Response.FETCH_PRODUCT_ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            System.out.println("An error occurred during fetching of the product: " + e.getMessage());
            throw e;
        }
        return product;

    }

    @Override
    public void deleteProduct(Long id) throws SQLException {
        int statusCode = 200;
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.DELETE_PRODUCT_BY_ID)) {
            stmt.setLong(1, id);
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println(Response.DELETE_PRODUCT_SUCCESS_MESSAGE);
                System.out.println(Response.successCode);
            } else {
                System.out.println(Response.DELETE_PRODUCT_ERROR_MESSAGE);
                System.out.println(Response.errorCode);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred during deleting of the product: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        List<Product> product = new ArrayList<>();
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.SELECT_ALL_PRODUCT)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                product.add(new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getLong("userId")
                ));
            }
            if (!product.isEmpty()) {
                System.out.println(Response.FETCH_ALL_PRODUCT_SUCCESS_MESSAGE);
            } else {
                System.out.println(Response.FETCH_ALL_PRODUCT_ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred during fetching: " + e.getMessage());
            throw e;
        }
        return null;
    }

    @Override
    public void update(Product product) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.UPDATE_PRODUCT)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(1, product.getPrice());
            stmt.setString(3, product.getDescription());
            int rowAffected = stmt.executeUpdate();
            if (rowAffected > 0) {
                System.out.println(Response.UPDATE_PRODUCT_SUCCESS_MESSAGE);
            } else {
                System.out.println(Response.UPDATEL_PRODUCT_ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred during product updated: " + e.getMessage());
            throw e;
        }
    }

}
