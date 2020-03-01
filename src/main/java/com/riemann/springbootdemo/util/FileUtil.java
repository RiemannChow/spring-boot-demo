package com.riemann.springbootdemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * 文件工具类
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            LOGGER.error("Close io stream error", e);
        }
    }
    }
}
