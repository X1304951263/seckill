package com.util;

import org.springframework.util.DigestUtils;

public class MD5 {
    public static final String salt = "xlwzws";

    public static String getMd5Word(String password){
        password += salt;
        String md5Word = DigestUtils.md5DigestAsHex(password.getBytes());
        return md5Word;
    }

    public static void main(String[] args) {
        System.out.println(getMd5Word("123"));
    }
}
