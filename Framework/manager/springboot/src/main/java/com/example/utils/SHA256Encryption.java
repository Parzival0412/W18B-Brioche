package com.example.utils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SHA256Encryption {

    private static final Logger logger = LoggerFactory.getLogger(SHA256Encryption.class);

    public static String encrypt(String input) {
        try {
            logger.info("开始对输入字符串进行 SHA-256 加密，输入: {}", input);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            String encrypted = Base64.getEncoder().encodeToString(hash);
            logger.info("SHA-256 加密成功，加密结果: {}", encrypted);
            return encrypted;
        } catch (NoSuchAlgorithmException e) {
            logger.error("在进行 SHA-256 加密时发生错误，未找到指定的加密算法", e);
            throw new RuntimeException(e);
        }
    }


}
