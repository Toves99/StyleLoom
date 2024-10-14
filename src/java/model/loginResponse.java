/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author cekesa
 */
public class loginResponse {
    private Long id;
    private String token;
     private String username;
    private String email;
    private String bio;
    private String socialLinks;
    private boolean isInfluencer;
    private String mobileNo;
  

    public loginResponse() {
    }

    public loginResponse(Long id, String token, String username, String email, String bio,String socialLinks, boolean isInfluencer, String mobileNo) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.socialLinks = socialLinks;
        this.isInfluencer = isInfluencer;
        this.mobileNo = mobileNo;
    }

   
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public String toString() {
        return "loginResponse{" + "id=" + id + ", token=" + token + ", username=" + username + ", email=" + email + ", bio=" + bio + ", socialLinks=" + socialLinks + ", isInfluencer=" + isInfluencer + ", mobileNo=" + mobileNo + '}';
    }
    
   
    
    
}
