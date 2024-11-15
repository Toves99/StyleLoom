/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author cekesa
 */
public class ProfilePhoto {
    private Long id;
    private Long userId;
    private String photoBase64;

    public ProfilePhoto() {
    }

    public ProfilePhoto(Long id, Long userId, String photoBase64) {
        this.id = id;
        this.userId = userId;
        this.photoBase64 = photoBase64;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    @Override
    public String toString() {
        return "ProfilePhoto{" + "id=" + id + ", userId=" + userId + ", photoBase64=" + photoBase64 + '}';
    }
    
}
