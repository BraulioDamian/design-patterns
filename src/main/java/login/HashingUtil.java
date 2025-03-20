/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

/**
 *
 * @author braul
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class HashingUtil {
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
        public static void main(String[] args) {
        String passwordToHash = "contraseñaAlexis";
                String passwordToHash2 = "contraseñaGerente";

        String hashedPassword = hashPassword(passwordToHash);
        System.out.println("La contraseña hasheada de '" + passwordToHash + "' es: " + hashedPassword);
           System.out.println("La contraseña hasheada de '" + passwordToHash2 + "' es: " + hashedPassword);

        }
    
}
