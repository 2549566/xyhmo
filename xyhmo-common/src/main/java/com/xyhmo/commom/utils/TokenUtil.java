package com.xyhmo.commom.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class TokenUtil {

    public static String genToken() throws NoSuchAlgorithmException {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
        MessageDigest md = MessageDigest.getInstance("md5");
        byte md5[] =  md.digest(token.getBytes());
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(md5);
    }
}
