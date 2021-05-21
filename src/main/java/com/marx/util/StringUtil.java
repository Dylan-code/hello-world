package com.marx.util;

import com.wobangkj.utils.KeyUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * 和操作字符相关的工具类
 * */
public class StringUtil {

    /**
     * 将字符串通过指定的符号分割成List
     * @param str 被分割的字符串
     * @param regex 分割符
     * @return 分割后的List数组
     * */
    public static List<String> stringToList(String str,String regex){
        return Arrays.asList(str.split(regex));
    }

    /**
     * 对指定字符串进行MD5加密
     * @param source 需要加密的字符串
     * @return 加密后的字符串
     * */
    public static String encrypt_MD5(String source){
        return KeyUtils.md5Hex(source);
    }

    public static void main(String[] args) {
        System.out.println(encrypt_MD5("123456"));
    }
}
