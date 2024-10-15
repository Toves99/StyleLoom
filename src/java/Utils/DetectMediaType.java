/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.IOException;

/**
 *
 * @author cekesa
 */
public class DetectMediaType {
     public static int detectMediaType(byte[] data) throws IOException {
        // Use a simple check for the file type based on the byte array
        if (data.length > 0) {
            // Check for common image signatures
            if (data[0] == (byte) 0xFF && data[1] == (byte) 0xD8) {
                return 0; // JPEG image
            } else if (data[0] == (byte) 0x89 && data[1] == (byte) 0x50) {
                return 0; // PNG image
            } else if (data[0] == (byte) 0x47 && data[1] == (byte) 0x49) {
                return 0; // GIF image
            }
            // Add more checks for other image formats if needed

            // Check for common video signatures
            // Example: Checking for MP4 (ISO Base Media File)
            if (data.length >= 4 && (data[0] == (byte) 0x00 && data[1] == (byte) 0x00 && data[2] == (byte) 0x00 && data[3] == (byte) 0x20)) {
                return 1; // Video type (example for MP4)
            }
            // Add more checks for other video formats if needed
        }
        throw new IllegalArgumentException("Unsupported media type");
    }
}
