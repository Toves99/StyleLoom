/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

/**
 *
 * @author cekesa
 */
import service.UserService;
import service.serviceimpl.UserServiceImpl;
import model.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
// For JSON handling

public class GetUserByIdServlet extends HttpServlet {
    
    private final UserService userService = new UserServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String idParam = request.getParameter("id");

        try {
            Long userId = Long.valueOf(idParam);
            User user = userService.getUserById(userId);

            if (user != null) {
                // Create a JSONObject to represent the user
                JSONObject jsonResponse = new JSONObject();
                try {
                    jsonResponse.put("id", user.getId());
                } catch (JSONException ex) {
                    Logger.getLogger(GetUserByIdServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                jsonResponse.put("username", user.getUsername());
                jsonResponse.put("email", user.getEmail());
                jsonResponse.put("socialLinks", user.getSocialLinks());
                jsonResponse.put("bio", user.getBio());

                // Write the response
                response.getWriter().write(jsonResponse.toString());
            } else {
                // If user is not found, send a 404 response with a message
                JSONObject errorResponse = new JSONObject();
                errorResponse.put("message", "User not found");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(errorResponse.toString());
            }
        } catch (NumberFormatException | SQLException e) {
            // Handle invalid user ID or database errors
            e.printStackTrace();
            JSONObject errorResponse = new JSONObject();
            try {
                errorResponse.put("message", "Invalid user ID");
            } catch (JSONException ex) {
                Logger.getLogger(GetUserByIdServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(errorResponse.toString());
        } catch (JSONException ex) {
            Logger.getLogger(GetUserByIdServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

