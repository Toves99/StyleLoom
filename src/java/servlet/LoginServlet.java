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
import model.loginResponse;
import org.json.JSONException;
import org.json.JSONObject;
import service.UserService;
import service.serviceimpl.UserServiceImpl;

/**
 *
 * @author cekesa
 */
public class LoginServlet extends HttpServlet{
     private final UserService useService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        JSONObject json = null;
        try {
            json = new JSONObject(stringBuilder.toString());
        } catch (JSONException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        String email = null;
        try {
            email = json.getString("email");
        } catch (JSONException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        String password = null;
        try {
            password = json.getString("password");
        } catch (JSONException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            loginResponse loginResponse = useService.login(email, password);
            if (loginResponse != null) {
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_OK);
                JSONObject responseJson = new JSONObject();
                try {
                    responseJson.put("mobileNo", loginResponse.getMobileNo());
                    responseJson.put("socialLinks", loginResponse.getSocialLinks());
                    responseJson.put("bio", loginResponse.getBio());
                    responseJson.put("email", loginResponse.getEmail());
                    responseJson.put("username", loginResponse.getUsername());
                    responseJson.put("token", loginResponse.getToken());
                    responseJson.put("id", loginResponse.getId());
                } catch (JSONException ex) {
                    Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                resp.getWriter().write(responseJson.toString());
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("{\"message\":\"Invalid credentials\"}");
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\":\"Internal server error\"}");
        }
    }
    
}
