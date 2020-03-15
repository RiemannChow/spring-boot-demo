package com.riemann.springbootdemo.customize.redis;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 协议层 RiemannProtocal
 * 负责RESP协议串的拼装
 */
public class RiemannProtocal {

    public static final String DOLLER = "$";
    public static final String ALLERSTIC = "*";
    public static final String CRLF = "\r\n";

    /**
     * 如SET请求      set riemann 0328
     *
     * *3\r\n        长度为3的数组
     * $3\r\n        第一个字符串长度为3
     * SET\r\n       第一个字符串为SET
     * $7\r\n        第二个字符串长度为7
     * riemann\r\n   第二个字符串为riemann
     * $4\r\n        第三个字符串长度为4
     * 0328\r\n      第三个字符串为0328
     * @param os
     * @param command
     * @param args
     */
    public static void sendCommand(OutputStream os, RiemannCommand command, byte[]... args) {
        StringBuilder sb = new StringBuilder();
        // todo 封装该数组的长度
        sb.append(ALLERSTIC).append(args.length + 1).append(CRLF);
        // todo 封装方法SET、GET
        sb.append(DOLLER).append(command.name().length()).append(CRLF);
        sb.append(command.name()).append(CRLF);
        // todo 封装参数
        for (byte[] arg : args) {
            sb.append(DOLLER).append(arg.length).append(CRLF);
            sb.append(new String(arg)).append(CRLF);
        }
        try {
            os.write(sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
