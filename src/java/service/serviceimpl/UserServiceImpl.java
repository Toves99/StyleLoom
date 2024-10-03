/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.serviceimpl;

import Utils.DatabaseConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import model.loginResponse;
import service.UserService;

/**
 *
 * @author cekesa
 */
public class UserServiceImpl implements UserService {

    private static final String INSERT_USER = "INSERT INTO users (username,email,bio,socialLinks,isInfluencer, password, token) VALUES (?, ?, ?,?,?,?,?)";
    private static final String SELECT_USER = "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String SELECT_TOKEN = "SELECT * FROM users WHERE token = ?";
    private static final String UPDATE_USER = "UPDATE users SET username = ?, email = ?, bio = ?, socialLinks = ?, isInfluencer = ?, password = ?, token = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

    @Override
    public void register(User user) throws SQLException {
        if (isEmailTaken(user.getEmail())) {
            throw new SQLException("User with the given email already exists.");
        }
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getBio());
            stmt.setString(4, user.getSocialLinks());
            stmt.setBoolean(5, false);
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getToken());  // Setting isInfluencer to false

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
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_USER)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new loginResponse(
                            rs.getLong("id"),
                            rs.getString("token")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_TOKEN)) {
            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void update(User user) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE_USER)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getBio());
            stmt.setString(4, user.getSocialLinks());
            stmt.setBoolean(5, user.isIsInfluencer());
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getToken());
            stmt.setLong(8, user.getId()); // Bind the user ID to identify which user to update

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
        }
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_USER_BY_EMAIL)) {
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
                            rs.getString("token")
                    // Ensure 'id' is an integer in the User model
                    );
                }
            }
        }
        return null;
    }

    public boolean isEmailTaken(String email) throws SQLException {
        String CHECK_EMAIL_EXISTS = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(CHECK_EMAIL_EXISTS)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    public void delete(long userId) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE_USER)) {
            stmt.setLong(1, userId); // Bind the user ID to specify which user to delete

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
        }

}
}
