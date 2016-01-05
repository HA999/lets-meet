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
 * @author leppa
 */
    
public final class PasswordService {

    //Encrypt string pass to a hashed SHA256 encrypted version
    public static String encrypt(String pass) {
            MessageDigest md = null;

            try {
                md = MessageDigest.getInstance("SHA-256");
            }catch (NoSuchAlgorithmException e) {
                //do something
            }
            try {
                md.update(pass.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                //do something
            }

            byte raw[] = md.digest();
            String hash = (new BASE64Encoder()).encode(raw);
            return hash;
    }
}
