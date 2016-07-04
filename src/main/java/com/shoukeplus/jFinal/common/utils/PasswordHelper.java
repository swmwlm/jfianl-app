package com.shoukeplus.jFinal.common.utils;

import com.shoukeplus.jFinal.common.model.AdminUser;
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

    public static void encryptPassword(AdminUser adminUser) {
        adminUser.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(
                algorithmName,
                adminUser.getPassword(),
                ByteSource.Util.bytes(adminUser.getUsername() + adminUser.getSalt()),
                hashIterations).toHex();
        adminUser.setPassword(newPassword);
    }

    public static void main(String[] strings){
        AdminUser adminUser=new AdminUser();
        adminUser.set("username", "admin")
                .set("password", "123123");
        encryptPassword(adminUser);
        System.out.println(adminUser.toJson());
    }
}