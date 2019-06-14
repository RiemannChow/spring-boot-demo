package com.riemann.springbootdemo.util.common;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;

/**
 * @author riemann
 * @date 2019/06/13 22:54
 */
@Configuration
class EncryptablePropertyConfig {

    private static final Logger logger = LoggerFactory.getLogger(EncryptablePropertyConfig.class);

    @Bean(name="encryptablePropertyResolver")
    public EncryptablePropertyResolver encryptablePropertyResolver() {
        return new EncryptionPropertyResolver();
    }

     class EncryptionPropertyResolver implements EncryptablePropertyResolver {

        @Override
        public String resolvePropertyValue(String value) {
            if (StringUtils.isBlank(value)) {
                return value;
            }
            //值以DES@开头的均为DES加密，需要解密
            if (value.startsWith("DES@")) {
                return resolveDESValue(value.substring(4));
            }
            //不需要解密的值直接返回
            return value;
        }

        private String resolveDESValue(String value) {
            //自定义DES密文解密
            //return JasyptUtil.decyptString(ParseSystemUtil.parseHexStr2Byte(value)));
            logger.info("AES@ sub value :" + value);
            return getOddValue(ParseSystemUtil.parseHexStr2Byte(value));
        }

         private String getOddValue(String parseHexStr2Byte) {
             StringBuilder result = new StringBuilder();
             for (int i = 0; i < parseHexStr2Byte.length(); i++) {
                 if (i % 2 == 0) {
                     result.append(parseHexStr2Byte.charAt(i));
                 }
             }
             return result.toString();
         }
    }

}
