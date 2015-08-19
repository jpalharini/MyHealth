package android.palharini.myhealth.activities;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptPassword {

    // Declare variables
    private String strCryptPassword;
    private BigInteger biHash;
    private MessageDigest md;

    public String encryptPassword (String strPassword) {
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(strPassword.getBytes("UTF-8"));
            biHash = new BigInteger(1, md.digest());
            strCryptPassword = biHash.toString(16);
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strCryptPassword;
    }


}
