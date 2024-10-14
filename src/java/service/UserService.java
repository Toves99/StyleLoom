/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.sql.SQLException;
import model.ProfilePhoto;
import model.User;
import model.loginResponse;

/**
 *
 * @author cekesa
 */
public interface UserService {
    void register(User user) throws SQLException;
    loginResponse login(String email, String password) throws SQLException;
    boolean validateToken(String token);
    void update(User user) throws SQLException;
    User getUserByEmail(String email) throws SQLException;
    void delete(long userId) throws SQLException;
    User getUserById(Long id) throws SQLException;
    void uploadPhoto(ProfilePhoto profilePhoto) throws SQLException;
    public ProfilePhoto getProfileImage(int userId);
    
}
