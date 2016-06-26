package com.langmy.jFinal.common.utils;

import com.langmy.jFinal.common.model.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private static String algorithmName = "md5";
    private static int hashIterations = 2;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public static void encryptPassword(User user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getUsername() + user.getSalt()),
                hashIterations).toHex();
        user.setPassword(newPassword);
    }

    public static void main(String[] strings){
        User user=new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("123456");
        user.setSalt("123456");
        encryptPassword(user);
        System.out.println(user.toString());
    }
}