/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;




import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

/**
 *
 * @author cekesa
 */
public class TokenUtil {
     private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();
     public static String generateToken() {
         // Generate a random UUID
        String uuid = UUID.randomUUID().toString();
        
        // Generate additional random bytes
        byte[] randomBytes = new byte[256]; // Adjust the length as needed
        random.nextBytes(randomBytes);
        
        // Encode the random bytes to a string
        String additionalRandomString = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        
        // Concatenate the UUID and the random string
        return uuid + "-" + additionalRandomString;
        
        
    }
}
