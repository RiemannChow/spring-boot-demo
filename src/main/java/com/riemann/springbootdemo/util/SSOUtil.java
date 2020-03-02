package com.riemann.springbootdemo.util;

import com.riemann.springbootdemo.model.UserSSO;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * @author riemann
 * @date 2019/06/30 0:16
 */
public class SSOUtil {

    /**
     * 加盐加密的策略非常多,根据实际业务来
     */
    public static void entryptPassword(UserSSO userSSO) {
        String salt = UUID.randomUUID().toString();
        String temPassword = salt + userSSO.getPlainPassword();
        String md5Password = DigestUtils.md5DigestAsHex(temPassword.getBytes());
        userSSO.setSalt(salt);
        userSSO.setPassword(md5Password);
    }

    public static boolean decryptPassword(UserSSO userSSO, String plainPassword) {
        String temPassword = userSSO.getSalt() + plainPassword;
        String md5Password = DigestUtils.md5DigestAsHex(temPassword.getBytes());
        return userSSO.getPassword().equals(md5Password);
    }

    public static String getCurrentDateTime() {
        TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(zone);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

}
