/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author cekesa
 */
public class Queries {
    public static final String INSERT_USER = "INSERT INTO users (username,email,bio,socialLinks,isInfluencer, password, token,mobileNo) VALUES (?, ?, ?,?,?,?,?,?)";
    public static final String SELECT_USER = "SELECT * FROM users WHERE email = ? AND password = ?";
    public static final String SELECT_TOKEN = "SELECT * FROM users WHERE token = ?";
    public static final String UPDATE_USER = "UPDATE users SET username = ?, email = ?, bio = ?, socialLinks = ?, isInfluencer = ?, password = ?, token = ?,mobileNo=? WHERE id = ?";
    public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    public static final String INSERT_PHOTO = "INSERT INTO userProfile (userId, photoData) VALUES (?, ?)";
    public static final String SELECT_PHOTO_BY_USERID = "SELECT * FROM userImage WHERE userId = ?";
    public static final String CHECK_EMAIL_EXISTS = "SELECT COUNT(*) FROM users WHERE email = ?";
    public static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    public static final String INSERT_MEDIA = "INSERT INTO media (userId, mediaData,type) VALUES (?, ?,?)";
    public static final String SELECT_MEDIA_BY_ID = "SELECT * FROM media WHERE id = ?";
    public static final String DELETE_MEDIA_BY_ID = "DELETE FROM photos WHERE id = ?";
    public static final String SELECT_ALL_MEDIA = "SELECT * FROM photos";
    public static final String INSERT_FOLLOWS = "INSERT INTO followers (followerId, followedId) VALUES (?, ?)";
    public static final String UNFOLLOW = "DELETE FROM followers WHERE followerId = ? AND followedId = ?";
    public static final String SELECT_FOLLOWERS = "SELECT follower_id, followed_id FROM followers WHERE followedId = ?";
    public static final String SELECT_FOLLOWING = "SELECT follower_id, followed_id FROM followers WHERE followerId = ?";
    public static final String SELECT_ISFOLLOWING = "SELECT * FROM followers WHERE followerId = ? AND followedId = ?";
    public static final String INSERT_PRODUCT = "INSERT INTO product (name,price,description,userId) VALUES (?, ?, ?,?)";
    public static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product WHERE id = ?";
    public static final String DELETE_PRODUCT_BY_ID = "DELETE FROM product WHERE id = ?";
    public static final String SELECT_ALL_PRODUCT = "SELECT * FROM products";
    public static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, price = ?, description = ?";
    
    
}
