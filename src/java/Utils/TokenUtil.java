/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.UUID;

/**
 *
 * @author cekesa
 */
public class TokenUtil {
     public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
