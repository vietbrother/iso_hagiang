/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author VIET_BROTHER
 */
public class EncodeDecodeUtils {

    public static SecretKeySpec key;
    static {
        try {
            key = createSecretKey(Constants.SECURE_SECRET_KEY.toCharArray(),
                Constants.SECURE_SALT.getBytes(), 
                Constants.SECURE_ITERATION_COUNT, 
                Constants.SECURE_KEY_LENGTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, GeneralSecurityException, UnsupportedEncodingException, IOException {
        // TODO code application logic here
//        String password = "123";
//        if (password == null) {
//            throw new IllegalArgumentException("Run with -Dpassword=<password>");
//        }
//
//        // The salt (probably) can be stored along with the encrypted data
//        byte[] salt = Constants.SECURE_SALT.getBytes();
//
//        // Decreasing this speeds down startup time and can be useful during testing, but it also makes it easier for brute force attackers
//        int iterationCount = Constants.SECURE_ITERATION_COUNT;//40000;
//        // Other values give me java.security.InvalidKeyException: Illegal key size or default parameters
//        int keyLength = Constants.SECURE_KEY_LENGTH;//128;
//        SecretKeySpec key = createSecretKey(password.toCharArray(),
//                salt, iterationCount, keyLength);

        String originalPassword = "abc";
        System.out.println("Original password: " + originalPassword);
        String encryptedPassword = encrypt(originalPassword, key);
        System.out.println("Encrypted password: " + encryptedPassword);
        String decryptedPassword = decrypt(encryptedPassword, key);
        System.out.println("Decrypted password: " + decryptedPassword);
    }
    private static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    private static String encrypt(String property, SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(property.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static String decrypt(String string, SecretKeySpec key) throws GeneralSecurityException, IOException {
        String iv = string.split(":")[0];
        String property = string.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException {
        return Base64.getDecoder().decode(property);
    }
    
    public static String encrypt(String password) throws GeneralSecurityException, UnsupportedEncodingException{
        return encrypt(password, key);
    }
    public static String decrypt(String password) throws GeneralSecurityException, UnsupportedEncodingException, IOException{
        return decrypt(password, key);
    }
}
