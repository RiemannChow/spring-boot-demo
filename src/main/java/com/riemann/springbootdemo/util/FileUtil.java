package com.riemann.springbootdemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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

    public static boolean isJarFile(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return false;
        }
        try(JarFile jf = new JarFile(file)) {
            Enumeration<JarEntry> e = jf.entries();
            while (e.hasMoreElements()) {
                e.nextElement();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static String getDigest(String algo, File file) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(algo);
        try(DigestInputStream dis = new DigestInputStream(new FileInputStream(file), md)) {
            byte[] buffer = new byte[4096];
            while (dis.read(buffer) > 0) {
            }
            StringBuilder sb = new StringBuilder();
            for (byte b : md.digest()) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }
    }

    public static File getOrCreateDir(String path) throws IOException {
        File dir = new File(path);
        if (dir.exists() && !dir.isDirectory()) {
            throw new IOException("path is not directory: " + path);
        }
        if (!dir.exists()) {
            Path absPath = Paths.get(path).toAbsolutePath().normalize();
            Files.createDirectories(absPath);
        }
        return dir;
    }

}
