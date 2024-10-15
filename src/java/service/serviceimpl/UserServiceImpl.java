/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.serviceimpl;

import Utils.DatabaseConnect;
import Utils.Queries;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ProfilePhoto;
import model.User;
import model.loginResponse;
import service.UserService;



/**
 *
 * @author cekesa
 */
public class UserServiceImpl implements UserService {

    @Override
    public void register(User user) throws SQLException {
        if (isEmailTaken(user.getEmail())) {
            throw new SQLException("User with the given email already exists.");
        }
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getBio());
            stmt.setString(4, user.getSocialLinks());
            stmt.setBoolean(5, false);
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getToken());
            stmt.setString(8,user.getMobileNo());// Setting isInfluencer to false

            int affectedRows = stmt.executeUpdate();

            // Check if the insertion was successful and retrieve the generated ID
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1)); // Set the generated ID to the user object
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            } else {
                throw new SQLException("Creating user failed, no rows affected.");
            }
        }
    }

    @Override
    public loginResponse login(String email, String password) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.SELECT_USER)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new loginResponse(
                            rs.getLong("id"),
                            rs.getString("token"),
                             rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("bio"),
                            rs.getString("socialLinks"),
                            rs.getBoolean("isInfluencer"),
                            rs.getString("mobileNo")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.SELECT_TOKEN)) {
            stmt.setString(1, token);
            System.out.println("Token being validated: " + token); // Debug statement
            try (ResultSet rs = stmt.executeQuery()) {
                boolean isValid = rs.next();
                System.out.println("Token validation query result: " + isValid); // Additional debug statement
                return isValid;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void update(User user) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.UPDATE_USER)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getBio());
            stmt.setString(4, user.getSocialLinks());
            stmt.setBoolean(5, user.isIsInfluencer());
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getToken());
            stmt.setString(8,user.getMobileNo());
            stmt.setLong(9, user.getId()); // Bind the user ID to identify which user to update

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
        }
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.SELECT_USER_BY_EMAIL)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getLong("id"), // Fetch Long id
                            rs.getString("username"), // Fetch username
                            rs.getString("email"), // Fetch email
                            rs.getString("bio"), // Fetch bio
                            rs.getString("password"), // Fetch password
                            rs.getString("socialLinks"), // Fetch socialLinks
                            rs.getBoolean("isInfluencer"), // Fetch boolean for isInfluencer
                            rs.getString("token"),
                            rs.getString("mobileNo")
                    // Ensure 'id' is an integer in the User model
                    );
                }
            }
        }
        return null;
    }

    public boolean isEmailTaken(String email) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.CHECK_EMAIL_EXISTS)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public void delete(long userId) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.DELETE_USER)) {
            stmt.setLong(1, userId); // Bind the user ID to specify which user to delete

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        }

    }
    
    @Override
    public User getUserById(Long id) throws SQLException {
        User user = null;
        // Replace this with your actual database connection and query logic
        String query = "SELECT * FROM users WHERE id = ?"; // Adjust table name and fields as necessary
        try (Connection conn = DatabaseConnect.getConnection(); // Make sure you have a method to get a connection
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Assuming you have a constructor that matches your database fields
                    user = new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("bio"),
                        rs.getString("password"), // if you want to retrieve it
                        rs.getString("socialLinks"),
                        rs.getBoolean("isInfluencer"),
                        rs.getString("token"),
                             rs.getString("mobileNo")// if you want to retrieve it
                    );
                }
            }
        }
        return user; // This will return null if the user is not found
    }

    @Override
    public void uploadPhoto(ProfilePhoto profilePhoto) throws SQLException {
        try(Connection conn=DatabaseConnect.getConnection();
            PreparedStatement stmt=conn.prepareStatement(Queries.INSERT_PHOTO,PreparedStatement.RETURN_GENERATED_KEYS)){
            stmt.setLong(1, profilePhoto.getUserId());
             byte[] photoData = Base64.getDecoder().decode(profilePhoto.getPhotoBase64());
             InputStream inputStream = new ByteArrayInputStream(photoData);
            stmt.setBinaryStream(2, inputStream, photoData.length);
            int affectedRows = stmt.executeUpdate();
            if(affectedRows>0){
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        profilePhoto.setId(generatedKeys.getLong(1)); // Set the generated ID to the photo object
                    } else {
                        throw new SQLException("Uploading profile failed, no ID obtained.");
                    }
                }
            }else {
                throw new SQLException("Uploading profile failed, no rows affected.");
            }
        }
    }

    @Override
    public ProfilePhoto getProfileImage(int userId) {
               ProfilePhoto profilePhoto = null;
    try (Connection conn = DatabaseConnect.getConnection();
         PreparedStatement stmt = conn.prepareStatement(Queries.SELECT_PHOTO_BY_USERID)) {
        stmt.setInt(1, userId);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Convert binary data to base64
                byte[] photoData = rs.getBytes("photoData");
                String photoBase64 = Base64.getEncoder().encodeToString(photoData);

                profilePhoto = new ProfilePhoto(
                    rs.getLong("id"),
                    rs.getLong("userId"),
                    photoBase64
                );
            }
        }
    }   catch (SQLException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    return profilePhoto; // Return the found ProfileImage or null if not found
    }
}
