/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.control.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 *
 * @author HANAN&OLYA
 */
    
public final class PasswordService {

    public static String encrypt(String pass) {
            MessageDigest md = null;

            try {
                md = MessageDigest.getInstance("SHA-256");
            }catch (NoSuchAlgorithmException e) {
                System.out.println(e);
            }
            try {
                md.update(pass.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                System.out.println(e);
            }

            byte raw[] = md.digest();
            String hash = (new BASE64Encoder()).encode(raw);
            return hash;
    }
}
