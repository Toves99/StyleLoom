/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MediaFile;
import org.json.JSONException;
import org.json.JSONObject;
import service.MediaFileService;
import service.serviceimpl.MediaFileServiceImpl;

/**
 *
 * @author cekesa
 */
public class MediaFileServlet extends HttpServlet{
    private final MediaFileService mediaFileService= new MediaFileServiceImpl();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
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
            String mediaBase64 = json.getString("mediaBase64");

            MediaFile mediaFile = new MediaFile();
            mediaFile.setUserId(userId);
            mediaFile.setMediaBase64(mediaBase64);
            
            // Create a list and add the single MediaFile object
            List<MediaFile> mediaFiles = new ArrayList<>();
            mediaFiles.add(mediaFile);

            mediaFileService.uploadMedia(mediaFiles);

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            JSONObject responseJson = new JSONObject();
            responseJson.put("message", "Media uploaded successfully");
            responseJson.put("mediaId", mediaFile.getId());
            resp.getWriter().write(responseJson.toString());

        } catch (SQLException | JSONException e) {
            try {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JSONObject responseJson = new JSONObject();
                responseJson.put("message", "Error processing media upload");
                responseJson.put("error", e.getMessage());
                resp.getWriter().write(responseJson.toString());
            } catch (JSONException ex) {
                Logger.getLogger( MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String action = req.getParameter("action");

        if ("getAll".equals(action)) {
            try {
                List<MediaFile> mediaFiles = mediaFileService.getAllMedias();
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_OK);
                JSONObject responseJson = new JSONObject();
                try {
                    responseJson.put("mediaFiles", mediaFiles);
                } catch (JSONException ex) {
                    Logger.getLogger(MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                resp.getWriter().write(responseJson.toString());
            } catch (SQLException e) {
                try {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    JSONObject responseJson = new JSONObject();
                    responseJson.put("message", "Error fetching photos");
                    responseJson.put("error", e.getMessage());
                    resp.getWriter().write(responseJson.toString());
                } catch (JSONException ex) {
                    Logger.getLogger(MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if ("getById".equals(action)) {
            Long mediaId;
            try {
                mediaId = Long.parseLong(req.getParameter("id"));
                MediaFile mediaFile = mediaFileService.getMediaById(mediaId);
                if (mediaFile != null) {
                    resp.setContentType("application/json");
                    resp.setStatus(HttpServletResponse.SC_OK);
                    JSONObject responseJson = new JSONObject();
                    try {
                        responseJson.put("MediaFile", mediaFile);
                    } catch (JSONException ex) {
                        Logger.getLogger(MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    resp.getWriter().write(responseJson.toString());
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JSONObject responseJson = new JSONObject();
                    try {
                        responseJson.put("message", "Media not found");
                    } catch (JSONException ex) {
                        Logger.getLogger(MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    resp.getWriter().write(responseJson.toString());
                }
            } catch (NumberFormatException e) {
                try {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    JSONObject responseJson = new JSONObject();
                    responseJson.put("message", "Invalid media ID format");
                    resp.getWriter().write(responseJson.toString());
                } catch (JSONException ex) {
                    Logger.getLogger(MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException e) {
                try {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    JSONObject responseJson = new JSONObject();
                    responseJson.put("message", "Error fetching photo");
                    responseJson.put("error", e.getMessage());
                    resp.getWriter().write(responseJson.toString());
                } catch (JSONException ex) {
                    Logger.getLogger(MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            try {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JSONObject responseJson = new JSONObject();
                responseJson.put("message", "Invalid action parameter");
                resp.getWriter().write(responseJson.toString());
            } catch (JSONException ex) {
                Logger.getLogger(MediaFileServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            try {
                mediaFileService.deleteMedia(id);
            } catch (SQLException ex) {
                Logger.getLogger(DeleteAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\":\"Media FIle deleted successfully\"}");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\":\"Invalid media ID\"}");
        }
    }
    
}
