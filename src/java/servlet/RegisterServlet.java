/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

import Utils.TokenUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import service.UserService;
import service.serviceimpl.UserServiceImpl;

/**
 *
 * @author cekesa
 */
public class RegisterServlet extends HttpServlet{
    
    private final UserService useService = new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            
            JSONObject json = new JSONObject(stringBuilder.toString());
            String username = json.getString("username");
            String password = json.getString("password");
            String email=json.getString("email");
            String bio=json.getString("bio");
            String socialLinks=json.getString("socialLinks");
            boolean isInfluencer=json.getBoolean("isInfluencer");
            
            
            try {
                String token = TokenUtil.generateToken();
                User user = new User(username, email, bio, password,socialLinks,isInfluencer, token);
                useService.register(user);
                
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_OK);
                JSONObject responseJson = new JSONObject();
                responseJson.put("message", "User registered successfully");
                responseJson.put("token", token);
                responseJson.put("id", user.getId()); // Add ID if available
                responseJson.put("email", user.getEmail());
                
                resp.getWriter().write(responseJson.toString());
            } catch (SQLException e) {
                JSONObject responseJson = new JSONObject();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"message\":\"Internal server error\"}");
                responseJson.put("error", e.getMessage());
                resp.getWriter().write(responseJson.toString());
                resp.setStatus(HttpServletResponse.SC_CONFLICT); // Use 409 Conflict for duplicate email
                responseJson.put("message", "User registration failed");
            }
        }   catch (JSONException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
