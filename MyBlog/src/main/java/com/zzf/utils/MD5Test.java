package com.zzf.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author zzf
 * @date 2021-02-14
 */
public class MD5Test {
    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("x520y520"));
    }
}
