/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author cekesa
 */
public class Profile {
    private Long userId;
    private String bio;
    private String socialLinks;

    public Profile() {
    }

    public Profile(Long userId, String bio, String socialLinks) {
        this.userId = userId;
        this.bio = bio;
        this.socialLinks = socialLinks;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Profile{" + "userId=" + userId + ", bio=" + bio + ", socialLinks=" + socialLinks + '}';
    }
    
    
}
