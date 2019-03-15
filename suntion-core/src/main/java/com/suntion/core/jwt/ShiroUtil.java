package com.suntion.core.jwt;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ShiroHelper shiro加密 删除Session
 * @author suns suntion@yeah.net
 * @since 2017年3月3日下午2:20:37
 */
public class ShiroUtil {
    /** logger */
    private static Logger logger = LoggerFactory.getLogger(ShiroUtil.class);

    /**随机数*/
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    /**
     * 随机一个盐值
     * @return 随机数
     */
    public static String randomSaltNumber() {
        return randomNumberGenerator.nextBytes().toHex();
    }

    /**
     * 密码加密
     * @param password 加密前密码
     * @param salt 盐值
     * @return 加密后密码
     */
    public static String encryptPassword(String password, String salt) {
        String algorithmName = "SHA-256";
        int hashIterations = 2;
        String newPassword = new SimpleHash(algorithmName,password, ByteSource.Util.bytes(salt), hashIterations).toHex();
        return newPassword;
    }

}
