/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.sql.SQLException;
import java.util.List;
import model.MediaFile;

/**
 *
 * @author cekesa
 */
public interface MediaFileService {
    void uploadMedia(List<MediaFile> mediaFiles) throws SQLException;
    MediaFile getMediaById(Long id) throws SQLException;
    void deleteMedia(Long id) throws SQLException;
    List<MediaFile> getAllMedias() throws SQLException;
}
