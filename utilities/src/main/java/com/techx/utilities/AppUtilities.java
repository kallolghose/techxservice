package com.techx.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

public class AppUtilities {

    private static Logger logger = LoggerFactory.getLogger(AppUtilities.class);

    /**
     * Function to get the secure phrase using a generated salt
     * @param phrase
     * @param salt
     * @return
     */
    public static String getSecurePhrase(String phrase, byte[] salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            byte[] bytes = md.digest(phrase.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Function to generate a salt.
     * @return
     */
    public static byte[] getSalt(){
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;
        }
        catch (Exception e){
            logger.error("Exception : " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Function to generate a random UDID
     * @return
     */
    public static String generateUDID(){
        return UUID.randomUUID().toString();
    }

}
