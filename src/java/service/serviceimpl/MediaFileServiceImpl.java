/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.serviceimpl;

import java.sql.SQLException;
import java.util.List;
import model.MediaFile;
import java.sql.Connection;
import Utils.DatabaseConnect;
import Utils.Queries;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import Utils.DetectMediaType;
import java.util.ArrayList;
import service.MediaFileService;

/**
 *
 * @author cekesa
 */
public class MediaFileServiceImpl implements MediaFileService {

    @Override
    public void uploadMedia(List<MediaFile> mediaFiles) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.INSERT_MEDIA, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Loop through each media file (image) and insert it into the database
            for (MediaFile mediaFile : mediaFiles) {
                stmt.setLong(1, mediaFile.getUserId());
                // Decode base64 to binary data
                byte[] mediaData = Base64.getDecoder().decode(mediaFile.getMediaBase64());
                InputStream inputStream = new ByteArrayInputStream(mediaData);
                stmt.setBinaryStream(2, inputStream, mediaData.length);

                // Detect media type and set the type in the mediaFile object
                int mediaType = DetectMediaType.detectMediaType(mediaData);
                mediaFile.setType(mediaType);
                stmt.setInt(3, mediaType); // 

                // Execute the insert for this image
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            mediaFile.setId(generatedKeys.getLong(1)); // Set the generated ID to the mediaFile object
                        } else {
                            throw new SQLException("Creating photo failed, no ID obtained.");
                        }
                    }
                } else {
                    throw new SQLException("Creating photo failed, no rows affected.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
        }
    }

    @Override
    public MediaFile getMediaById(Long id) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.SELECT_MEDIA_BY_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Convert binary data to base64
                    byte[] mediaData = rs.getBytes("mediaData");
                    String mediaBase64 = Base64.getEncoder().encodeToString(mediaData);
                    int mediaType = rs.getInt("type");
                    return new MediaFile(
                            rs.getLong("id"),
                            rs.getLong("userId"),
                            mediaBase64,
                            mediaType
                    );
                }
            }

        }
        return null;
    }

    @Override
    public void deleteMedia(Long id) throws SQLException {
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.DELETE_MEDIA_BY_ID)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<MediaFile> getAllMedias() throws SQLException {
        List<MediaFile> mediaFiles = new ArrayList<>();
        try (Connection conn = DatabaseConnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(Queries.SELECT_ALL_MEDIA); ResultSet rs = stmt.executeQuery()) {
            while(rs.next()){
                // Convert binary data to base64
                byte[] mediaData=rs.getBytes("mediaData");
                String mediaBase64=Base64.getEncoder().encodeToString(mediaData);
                int mediaType = rs.getInt("type");
                mediaFiles.add(new MediaFile(
                        rs.getLong("id"),
                        rs.getLong("userId"),
                        mediaBase64,
                        mediaType
                        
                ));
            }
        }
        return null;
    }

}
