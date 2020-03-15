package com.riemann.springbootdemo.customize.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * 传输层 RiemannRedisConnection
 * 负责连接的建立，数据发送与接收。
 */
public class RiemannRedisConnection {

    private Socket socket;
    private String host;
    private Integer port;
    private OutputStream outputStream;
    private InputStream inputStream;

    public RiemannRedisConnection(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 建立连接
     * @return
     */
    public RiemannRedisConnection connection() {
        try {
            socket = new Socket(host, port);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 向redis-server服务端发送操作命令
     * @param command
     * @param args
     * @return
     */
    public RiemannRedisConnection sendCommand(RiemannCommand command, byte[]... args) {
        connection();
        RiemannProtocal.sendCommand(outputStream, command, args);
        return this;
    }

    /**
     * redis-server服务端端给的响应
     * @return
     */
    public String response() {
        byte[] bytes = new byte[1024];
        try {
            inputStream.available();
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(bytes, Charset.defaultCharset());
    }
}
