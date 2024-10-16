/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Product;
import org.json.JSONException;
import org.json.JSONObject;
import service.ProductService;
import service.serviceimpl.ProductServiceImpl;
import Utils.Response;
import java.util.List;
import model.MediaFile;

/**
 *
 * @author cekesa
 */
public class ProductServlet extends HttpServlet {

    private final ProductService productService = new ProductServiceImpl();
   

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                JSONObject json = new JSONObject(stringBuilder.toString());
                Long userId = json.getLong("userId");
                String name = json.getString("name");
                Double price = json.getDouble("price");
                String description = json.getString("description");

                Product product = new Product();
                product.setUserId(userId);
                product.setName(name);
                product.setPrice(price);
                product.setDescription(description);
                productService.addProduct(product);

                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_OK);
                JSONObject responseJson = new JSONObject();
                responseJson.put("message",Response.ADD_PRODUCT_SUCCESS_MESSAGE);
                responseJson.put("statusCode",Response.successCode);
                responseJson.put("productId", product.getId());
                resp.getWriter().write(responseJson.toString());
            }
        } catch (SQLException | JSONException e) {
            try {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JSONObject responseJson = new JSONObject();
                responseJson.put("message", Response.ADD_PRODUCT_ERROR_MESSAGE);
                responseJson.put("error", Response.ADD_PRODUCT_ERROR_MESSAGE);
                resp.getWriter().write(responseJson.toString());
            } catch (JSONException ex) {
                Logger.getLogger(ProfilePhotoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("getAll".equals(action)) {
            try {
                List<Product> products = productService.getAllProducts();
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_OK);
                JSONObject responseJson = new JSONObject();
                try {
                    responseJson.put("statusCode", Response.successCode);
                    responseJson.put("statusCode", Response.FETCH_ALL_PRODUCT_SUCCESS_MESSAGE);
                    responseJson.put("products", products);
                } catch (JSONException ex) {
                    Logger.getLogger(MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                resp.getWriter().write(responseJson.toString());
            } catch (SQLException e) {
                try {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    JSONObject responseJson = new JSONObject();
                    responseJson.put("message",Response.FETCH_ALL_PRODUCT_ERROR_MESSAGE);
                    responseJson.put("error", e.getMessage());
                    resp.getWriter().write(responseJson.toString());
                } catch (JSONException ex) {
                    Logger.getLogger(MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if ("getById".equals(action)) {
            Long productId;
            try {
                productId = Long.parseLong(req.getParameter("id"));
                Product product = productService.getProductById(productId);
                if (product != null) {
                    resp.setContentType("application/json");
                    resp.setStatus(HttpServletResponse.SC_OK);
                    JSONObject responseJson = new JSONObject();
                    try {
                        responseJson.put("statusCode", Response.successCode);
                        responseJson.put("message", Response.FETCH_PRODUCT_SUCCESS_MESSAGE);
                        responseJson.put("product", product);
                    } catch (JSONException ex) {
                        Logger.getLogger(MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    resp.getWriter().write(responseJson.toString());
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JSONObject responseJson = new JSONObject();
                    try {
                        responseJson.put("message", Response.FETCH_PRODUCT_ERROR_MESSAGE);
                    } catch (JSONException ex) {
                        Logger.getLogger(MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    resp.getWriter().write(responseJson.toString());
                }
            } catch (NumberFormatException e) {
                try {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    JSONObject responseJson = new JSONObject();
                    responseJson.put("message", "Invalid product ID format");
                    resp.getWriter().write(responseJson.toString());
                } catch (JSONException ex) {
                    Logger.getLogger(MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException e) {
                try {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    JSONObject responseJson = new JSONObject();
                    responseJson.put("message", Response.FETCH_PRODUCT_ERROR_MESSAGE);
                    responseJson.put("error", Response.FETCH_PRODUCT_ERROR_MESSAGE);
                    resp.getWriter().write(responseJson.toString());
                } catch (JSONException ex) {
                    Logger.getLogger(MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }

}
