/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

import Utils.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Order;
import model.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import service.OrderService;
import service.serviceimpl.OrderServiceImpl;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 *
 * @author cekesa
 */
public class OrderServlet extends HttpServlet {

    private final OrderService orderService = new OrderServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Read order data from request
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                stringBuilder.append(line);
            }
            JSONObject json = new JSONObject(stringBuilder.toString());
            Long userId = json.getLong("userId");
            double totalAmount = json.getDouble("totalAmount");
            JSONArray productArray = json.getJSONArray("productIds");
            List<Product> products = new ArrayList<>();

            // Create Product objects from productIds
            for (int i = 0; i < productArray.length(); i++) {
                Product product = new Product();
                product.setId(productArray.getLong(i));
                products.add(product);
            }

            Order order = new Order();
            order.setUserId(userId);
            order.setProducts(products);
            order.setTotalAmount(totalAmount);

            orderService.addOrder(order);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            JSONObject responseJson = new JSONObject();
            responseJson.put("message", Response.ADD_ORDER_SUCCESS_MESSAGE);
            responseJson.put("statusCode", Response.successCode);
            responseJson.put("orderId", order.getId());
            response.getWriter().write(responseJson.toString());

        } catch (SQLException | JSONException e) {
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JSONObject responseJson = new JSONObject();
                responseJson.put("message", Response.ADD_ORDER_ERROR_MESSAGE);
                responseJson.put("error", Response.ADD_ORDER_ERROR_MESSAGE);
                response.getWriter().write(responseJson.toString());
            } catch (JSONException ex) {
                Logger.getLogger(ProfilePhotoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("application/json");
        try {
            if ("getAll".equals(action)) {
                List<Order> orders = orderService.getAllOrders();
                JSONArray jsonOrders = convertToJson(orders);
                response.getWriter().write(jsonOrders.toString());

            } else if ("getByUserId".equals(action)) {
                String userId = request.getParameter("userId");
                List<Order> orders = orderService.getOrdersByUserId(userId);
                JSONArray jsonOrders = convertToJson(orders);
                response.getWriter().write(jsonOrders.toString());
                
            }
        } catch (SQLException e) {
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(new JSONObject().put("error", "Error fetching orders: " + e.getMessage()).toString());
            } catch (JSONException ex) {
                Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JSONException ex) {
            Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            Long orderId = Long.valueOf(request.getParameter("orderId"));
            orderService.deleteOrder(orderId);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(new JSONObject().put("message", "Order deleted successfully").toString());

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write(new JSONObject().put("error", "Error deleting order: " + e.getMessage()).toString());
            } catch (JSONException ex) {
                Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.getWriter().write(new JSONObject().put("error", "Invalid order ID: " + e.getMessage()).toString());
            } catch (JSONException ex) {
                Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JSONException ex) {
            Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    // Helper method to convert a list of orders to a JSONArray
    private JSONArray convertToJson(List<Order> orders) throws JSONException {
        JSONArray jsonOrders = new JSONArray();
        for (Order order : orders) {
            JSONObject jsonOrder = new JSONObject();
            jsonOrder.put("id", order.getId());
            jsonOrder.put("userId", order.getUserId());
            jsonOrder.put("totalAmount", order.getTotalAmount());

            // Convert products to JSON
            JSONArray jsonProducts = new JSONArray();
            for (Product product : order.getProducts()) {
                JSONObject jsonProduct = new JSONObject();
                jsonProduct.put("id", product.getId());
                jsonProduct.put("name", product.getName());
                jsonProduct.put("price", product.getPrice());
                jsonProduct.put("description", product.getDescription());
                jsonProducts.put(jsonProduct);
            }
            jsonOrder.put("products", jsonProducts);
            jsonOrders.put(jsonOrder);
        }
        return jsonOrders;
    }
}
