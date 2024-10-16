/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.sql.SQLException;
import java.util.List;
import model.Product;

/**
 *
 * @author cekesa
 */
public interface ProductService {

    void addProduct(Product product) throws SQLException;

    Product getProductById(Long id) throws SQLException;

    void deleteProduct(Long id) throws SQLException;

    List<Product> getAllProducts() throws SQLException;

    void update(Product product) throws SQLException;
}
