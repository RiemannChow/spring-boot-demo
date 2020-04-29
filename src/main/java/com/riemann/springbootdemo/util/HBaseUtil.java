package com.riemann.springbootdemo.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public class HBaseUtil {

    //HBase Connection 创建是一个耗资源的过程
    //一般只创建一个 Connection 实例，其它地方共享该实例
    private static Connection conn = null;
    static {
        String zkHost = "192.168.0.100,192.168.0.101,192.168.0.102"; //zookeeper 集群
        String zkPort = "2181"; //端口
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", zkHost);
        conf.set("hbase.zookeeper.property.clientPort", zkPort);
        try {
            conn = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() {
        if (null == conn) throw new RuntimeException("HBase connection init failed...");
        return conn;
    }

    public static Table getTable(String tableName) throws IOException{
        return getTable(TableName.valueOf(tableName));
    }

    public static Table getTable(TableName tableName) throws IOException{
        return getConnection().getTable(tableName);
    }

    public static void put(TableName tableName, Put put) throws IOException {
        Table table = null;
        try {
            table = getTable(tableName);
            table.put(put);
        } finally {
            close(table);
        }
    }

    public static Object[] put(TableName tableName, List<Put> batch) throws IOException, InterruptedException {
        Table table = null;
        try {
            Object[] result = new Object[batch.size()];
            table = getTable(tableName);
            table.batch(batch, result);
            return result;
        } finally {
            close(table);
        }
    }

    public static synchronized void close() {
        if (null == null || conn.isClosed()) return;
        close(conn);
    }

    public static void close(Closeable c) {
        if (null == c) return;
        try {
            c.close();
        } catch (IOException e) {
            throw new RuntimeException("Error happened while closing " + c, e);
        }
    }

}
