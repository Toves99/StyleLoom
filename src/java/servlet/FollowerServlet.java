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
import model.Follower;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.FollowerService;
import service.serviceimpl.FollowerServiceImpl;

/**
 *
 * @author cekesa
 */
public class FollowerServlet extends HttpServlet{
    private final FollowerService followerService = new FollowerServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        try {
            JSONObject json = null;
            try {
                json = new JSONObject(jsonBuffer.toString());
            } catch (JSONException ex) {
                Logger.getLogger(FollowerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            Long followerId = json.getLong("followerId");
            Long followedId = json.getLong("followedId");

            String action = json.getString("action");
            if (action.equals("follow")) {
                try {
                    followerService.follow(new Follower(followerId, followedId));
                } catch (SQLException ex) {
                    Logger.getLogger(FollowerServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                resp.getWriter().write(new JSONObject().put("message", "Successfully followed").toString());
            } else if (action.equals("unfollow")) {
                followerService.unfollow(followerId, followedId);
                resp.getWriter().write(new JSONObject().put("message", "Successfully unfollowed").toString());
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(new JSONObject().put("error", "Invalid action").toString());
            }
        } catch (JSONException | SQLException ex) {
            Logger.getLogger(FollowerServlet.class.getName()).log(Level.SEVERE, null, ex);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                resp.getWriter().write(new JSONObject().put("error", ex.getMessage()).toString());
            } catch (JSONException ex1) {
                Logger.getLogger(FollowerServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
     @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Long userId = Long.parseLong(req.getParameter("userId"));

        try {
            JSONArray responseArray = new JSONArray();
            if ("followers".equals(action)) {
                followerService.getFollowers(userId).forEach(f -> {
                    try {
                        responseArray.put(new JSONObject().put("followerId", f.getFollowerId()));
                    } catch (JSONException ex) {
                        Logger.getLogger(FollowerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } else if ("following".equals(action)) {
                followerService.getFollowing(userId).forEach(f -> {
                    try {
                        responseArray.put(new JSONObject().put("followedId", f.getFollowedId()));
                    } catch (JSONException ex) {
                        Logger.getLogger(FollowerServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(new JSONObject().put("error", "Invalid action").toString());
                return;
            }

            resp.setContentType("application/json");
            resp.getWriter().write(responseArray.toString());

        } catch (SQLException | JSONException ex) {
            Logger.getLogger(FollowerServlet.class.getName()).log(Level.SEVERE, null, ex);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                resp.getWriter().write(new JSONObject().put("error", ex.getMessage()).toString());
            } catch (JSONException ex1) {
                Logger.getLogger(FollowerServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
}
