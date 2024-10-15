/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author cekesa
 */
public class MediaFile {
    private Long id;
    private Long userId;
    private String mediaBase64;
    private int type;

    public MediaFile() {
    }

    public MediaFile(Long id, Long userId, String mediaBase64, int type) {
        this.id = id;
        this.userId = userId;
        this.mediaBase64 = mediaBase64;
        this.type = type;
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

    public String getMediaBase64() {
        return mediaBase64;
    }

    public void setMediaBase64(String photoBase64) {
        this.mediaBase64 = photoBase64;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MediaFile{" + "id=" + id + ", userId=" + userId + ", photoBase64=" + mediaBase64 + ", type=" + type + '}';
    }
    
    
}
