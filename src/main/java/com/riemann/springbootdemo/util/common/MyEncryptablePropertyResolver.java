package com.riemann.springbootdemo.util.common;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.apache.catalina.security.SecurityUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author riemann
 * @date 2019/06/13 0:19
 */
public class MyEncryptablePropertyResolver implements EncryptablePropertyResolver {

    @Value("${spring.datasource.password}")
    private String dataKey;

    @Override
    public String resolvePropertyValue(String s) {
        if (null != s && s.startsWith(MyEncryptablePropertyDetector.ENCODED_PASSWORD_HINT)) {
            //对配置文件加密值进行解密。加密方式可以自定义
            //楼主这里使用hutool包提供的AES加密,秘钥经过异或并base64写入配置文件
            s = s.substring(MyEncryptablePropertyDetector.ENCODED_PASSWORD_HINT.length());
            byte[] decrypt = DESEncrypt.decrypt(Base64.decodeBase64(dataKey));
            System.out.println(decrypt);
        }
        return null;
    }
}
