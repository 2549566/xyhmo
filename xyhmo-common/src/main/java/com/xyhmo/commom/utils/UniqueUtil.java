package com.xyhmo.commom.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class UniqueUtil {

    public static String genToken() throws NoSuchAlgorithmException {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
        return md5(token);
    }

    public static String genPin(String mobile){
        String date = System.currentTimeMillis()+"";
        String dateStr=date.substring(date.length()-6);
        return "BJ"+mobile+dateStr;
    }

    public static String md5(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for(int i=0;i<bits.length;i++){
            int a = bits[i];
            if(a<0) a+=256;
            if(a<16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }

    public static void main(String[] args) {

    }
}
