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
import model.ProfilePhoto;
import org.json.JSONException;
import org.json.JSONObject;
import service.UserService;
import service.serviceimpl.UserServiceImpl;

/**
 *
 * @author cekesa
 */
public class ProfilePhotoServlet extends HttpServlet{
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
            Long userId = json.getLong("userId");
            String photoBase64 = json.getString("photoBase64");

            ProfilePhoto profilePhoto = new ProfilePhoto();
            profilePhoto.setUserId(userId);
            profilePhoto.setPhotoBase64(photoBase64);

            useService.uploadPhoto(profilePhoto);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            JSONObject responseJson = new JSONObject();
            responseJson.put("message", "Profile uploaded successfully");
            responseJson.put("photoId", profilePhoto.getId());
            resp.getWriter().write(responseJson.toString());

        } catch (SQLException | JSONException e) {
            try {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JSONObject responseJson = new JSONObject();
                responseJson.put("message", "Error processing profile upload");
                responseJson.put("error", e.getMessage());
                resp.getWriter().write(responseJson.toString());
            } catch (JSONException ex) {
                Logger.getLogger(ProfilePhotoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(req.getParameter("userId")); // Retrieve userId from request parameters
            ProfilePhoto profilePhoto = useService.getProfileImage(userId);

            resp.setContentType("application/json");
            if (profilePhoto != null) {
                JSONObject responseJson = new JSONObject();
                responseJson.put("userId", profilePhoto.getUserId());
                responseJson.put("photoBase64", profilePhoto.getPhotoBase64());
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(responseJson.toString());
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JSONObject responseJson = new JSONObject();
                responseJson.put("message", "Profile image not found");
                resp.getWriter().write(responseJson.toString());
            }
        } catch (NumberFormatException e) {
            try {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JSONObject responseJson = new JSONObject();
                responseJson.put("message", "Invalid userId format");
                resp.getWriter().write(responseJson.toString());
            } catch (JSONException ex) {
                Logger.getLogger(ProfilePhotoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JSONException e) {
            Logger.getLogger(ProfilePhotoServlet.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
