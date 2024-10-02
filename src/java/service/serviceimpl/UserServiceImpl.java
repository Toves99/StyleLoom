/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.serviceimpl;

import java.sql.SQLException;
import model.User;
import model.loginResponse;
import service.UserService;

/**
 *
 * @author cekesa
 */
public class UserServiceImpl implements UserService{
    private static final String INSERT_USER = "INSERT INTO users (username,email,bio,socialLinks,isInfluencer, password, token) VALUES (?, ?, ?,?,?,?,?)";
    private static final String SELECT_USER = "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String SELECT_TOKEN = "SELECT * FROM users WHERE token = ?";
  
    @Override
    public void register(User user) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public loginResponse login(String email, String password) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean validateToken(String token) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User user) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
