/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author cekesa
 */
public class User {
    private Long id;
    private String username;
    private String email;
    private String bio;
    private String password;
    private String socialLinks;
    private boolean isInfluencer;
    private String token;

    public User() {
    }

    public User(String username, String email, String bio, String password, String socialLinks, boolean isInfluencer,String token) {
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.password = password;
        this.socialLinks = socialLinks;
        this.isInfluencer = isInfluencer;
        this.token = token;
    }
    
    public User(Long id,String username, String email, String bio, String password, String socialLinks, boolean isInfluencer,String token) {
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.password = password;
        this.socialLinks = socialLinks;
        this.isInfluencer = isInfluencer;
        this.token = token;
        this.id=id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(String socialLinks) {
        this.socialLinks = socialLinks;
    }

    public boolean isIsInfluencer() {
        return isInfluencer;
    }

    public void setIsInfluencer(boolean isInfluencer) {
        this.isInfluencer = isInfluencer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", email=" + email + ", bio=" + bio + ", password=" + password + ", socialLinks=" + socialLinks + ", isInfluencer=" + isInfluencer + '}';
    }
    
    
}
