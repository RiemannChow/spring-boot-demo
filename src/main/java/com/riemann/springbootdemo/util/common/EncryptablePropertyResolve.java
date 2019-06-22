package com.riemann.springbootdemo.util.common;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            //值以DES@开头的均为DES加密，需要解密throws Exception
            if (value.startsWith("DES@")) {
                try {
                    return resolveDESValue(value.substring(4));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //不需要解密的值直接返回
            return value;
        }

        private String resolveDESValue(String value) throws Exception {
            //自定义DES密文解密
            logger.info("AES@ sub value :" + value);
            String riemann = JasyptUtil.decryption(value, "riemann");
            logger.info("riemann DES: " + riemann);
            return riemann;
        }

    }


}
