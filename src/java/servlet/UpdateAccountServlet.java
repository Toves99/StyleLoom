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
import model.User;
import org.json.JSONException;
import org.json.JSONObject;
import service.UserService;
import service.serviceimpl.UserServiceImpl;

/**
 *
 * @author cekesa
 */
public class UpdateAccountServlet extends HttpServlet{
   private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Reading JSON data from request body
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }

            JSONObject json = new JSONObject(stringBuilder.toString());
            Long userId = json.getLong("id");  // Assuming ID is provided in request body
            String username = json.getString("username");
            String email = json.getString("email");
            String bio = json.getString("bio");
            String socialLinks = json.getString("socialLinks");
            boolean isInfluencer = json.getBoolean("isInfluencer");
            String mobileNo=json.getString("mobileNo");

            // Fetch the existing user to retain the password and token
            User existingUser = userService.getUserById(userId);
            if (existingUser == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"User not found\"}");
                return;
            }

            // Updating only specified fields, leaving password and token unchanged
            existingUser.setUsername(username);
            existingUser.setEmail(email);
            existingUser.setBio(bio);
            existingUser.setSocialLinks(socialLinks);
            existingUser.setIsInfluencer(isInfluencer);
            existingUser.setMobileNo(mobileNo);

            // Call the update method with the modified user object
            userService.update(existingUser);

            resp.setContentType("application/json");
            resp.getWriter().write("{\"message\": \"User updated successfully\"}");
        } catch (SQLException e) {
            Logger.getLogger(UpdateAccountServlet.class.getName()).log(Level.SEVERE, null, e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Failed to update user\"}");
        } catch (JSONException ex) {
            Logger.getLogger(UpdateAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid JSON input\"}");
        }
    }
}
