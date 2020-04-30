package com.riemann.springbootdemo.util;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类主要演示下面几点功能：
 * 连接复用
 * 多线程操作
 * 批量写入
 * WAL 关闭
 * 指定范围扫描
 * 过滤器扫描
 */
public class HBaseClient {

    private static TableName TABLE_NAME = TableName.valueOf("riemann");
    private static byte[] COL_FAMILY = Bytes.toBytes("cf");
    private static byte[] QUALIFY_NAME = Bytes.toBytes("name");
    private static byte[] QUALIFY_CLASS = Bytes.toBytes("class");

    /**
     * HBase Table 不是线程安全类
     * 在多线程的场合，不能多线程共享相同 Table 实例
     *
     * @throws IOException
     */
    public static void safePut() throws IOException {
        Put riemann = new Put(Bytes.toBytes("id_1"));
        riemann.addColumn(COL_FAMILY, QUALIFY_NAME, Bytes.toBytes("riemann"));
        riemann.addColumn(COL_FAMILY, QUALIFY_CLASS, Bytes.toBytes("classA"));
        HBaseUtil.put(TABLE_NAME, riemann);
    }

    /**
     * 批量写入，可以提高写入性能（减少 RPC）
     * HBase 客户端会自动打包并请求相应的 RegionServer
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void batchPut() throws IOException, InterruptedException {
        int batchSize = 10;
        List<Put> batch = new ArrayList<Put>();
        for (int i = 0; i < batchSize; i++) {
            Put put = new Put(Bytes.toBytes("id_" + i));
            put.addColumn(COL_FAMILY, QUALIFY_NAME, Bytes.toBytes("name_" + i));
            put.addColumn(COL_FAMILY, QUALIFY_CLASS, Bytes.toBytes("class_" + i));
            batch.add(put);
        }
        HBaseUtil.put(TABLE_NAME, batch);
    }

    /**
     * WAL 作用是当 RegionServer 宕机是可以 replay 来恢复数据（即提高可靠性）
     * 如果写入数据容忍丢失，那么可以选择关闭 WAL，写入性能可以提升 2～3 倍
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void walOffPut() throws IOException, InterruptedException {
        int batchSize = 10;
        List<Put> batch = new ArrayList<Put>();
        for (int i = 0; i < batchSize; i++) {
            Put put = new Put(Bytes.toBytes("id_" + i));
            put.addColumn(COL_FAMILY, QUALIFY_NAME, Bytes.toBytes("name_" + i));
            put.addColumn(COL_FAMILY, QUALIFY_CLASS, Bytes.toBytes("class_" + i));

            //关闭 WAL，可以提升2～3倍的性能，但有数据丢失风险
            put.setDurability(Durability.SKIP_WAL);
            batch.add(put);
        }
        HBaseUtil.put(TABLE_NAME, batch);
    }

    /**
     * 扫描时指定开始行和结束行，避免全表扫描
     * 在表记录数非常多的情况下，效果非常明显
     *
     * @throws IOException
     */
    public static void rangeScan() throws IOException {
        Scan scan = new Scan();
        //指定扫描 [id_11,id_22) 之间的数据
        byte[] startRow = Bytes.toBytes("id_11");
        byte[] stopRow = Bytes.toBytes("id_22");
        scan.setStartRow(startRow); //指定扫描开始行
        scan.setStopRow(stopRow); //指定扫描结束行
        Table table = null;
        ResultScanner rs = null;
        try {
            table = HBaseUtil.getTable(TABLE_NAME);
            rs = table.getScanner(scan);
            for (Result r : rs) {
                String r_id = Bytes.toString(r.getRow());
                String r_name = Bytes.toString(r.getValue(COL_FAMILY, QUALIFY_NAME));
                String r_class = Bytes.toString(r.getValue(COL_FAMILY, QUALIFY_CLASS));
            }
        } finally {
            HBaseUtil.close(rs);
            HBaseUtil.close(table);
        }
    }

    /**
     * 过滤器扫描（返回过滤器指定条件的记录，即类似于搜索功能）
     *
     * @throws IOException
     */
    public static void filterScan() throws IOException {
        SingleColumnValueFilter filter = new SingleColumnValueFilter(COL_FAMILY, QUALIFY_NAME, CompareFilter.CompareOp.EQUAL, Bytes.toBytes("riemann"));
        Scan scan = new Scan();
        scan.setFilter(filter);
        Table table = null;
        ResultScanner rs = null;
        try {
            table = HBaseUtil.getTable(TABLE_NAME);
            rs = table.getScanner(scan);
            for (Result r : rs) {
                String r_id = Bytes.toString(r.getRow());
                String r_name = Bytes.toString(r.getValue(COL_FAMILY, QUALIFY_NAME));
                String r_class = Bytes.toString(r.getValue(COL_FAMILY, QUALIFY_CLASS));
            }
        } finally {
            HBaseUtil.close(rs);
            HBaseUtil.close(table);
        }
    }

}
