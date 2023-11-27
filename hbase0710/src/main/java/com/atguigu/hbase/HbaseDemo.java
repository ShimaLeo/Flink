package com.atguigu.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Hbase的API操作
 */

public class HbaseDemo {

    //获取连接
    public static Connection getHbaseConnection(){
        try {
            Configuration conf = new Configuration();
            conf.set("hbase.zookeeper.quorum","hadoop102,hadoop103,hadoop104");
            conf.set("hbase.zookeeper.property.clientPort","2181");
            Connection connection = ConnectionFactory.createConnection(conf);
            return connection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //关闭连接
    public static void closeHbaseConnection(Connection connection){
        if (connection != null && !connection.isClosed()){
            try {
                connection.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    /**
     * 建表
     * @param connection    连接
     * @param namespace     表空间
     * @param tableName     表名
     * @param families      列族
     *      Admin: 对Hbase进行DDL操作的对象
     */
    public static void createTable(Connection connection,String namespace,String tableName, String... families){

        if (families.length < 1){
            System.out.println("列族没有指定");
            return;
        }



        try (Admin admin = connection.getAdmin()){
            TableName tableNameObj = TableName.valueOf(namespace, tableName);

            if (admin.tableExists(tableNameObj)){
                System.out.println("要创建的表已经存在");
                return;
            }


            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableNameObj);

            for (int i = 0; i < families.length; i++) {
                String family = families[i];

                ColumnFamilyDescriptor columnFamilyDescriptor= ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(family)).build();

                tableDescriptorBuilder.setColumnFamily(columnFamilyDescriptor);
            }

            admin.createTable(tableDescriptorBuilder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void dropTable(Connection connection,String namespace,String tableName){


        try (Admin admin = connection.getAdmin()){
            TableName tableNameObj = TableName.valueOf(namespace, tableName);

            if (!admin.tableExists(tableNameObj)){
                System.out.println("要删除的表不存在");
                return;
            }

            admin.disableTable(tableNameObj);
            admin.deleteTable(tableNameObj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        Connection hbaseConnection = getHbaseConnection();

        //建表
        //createTable(hbaseConnection,"bigdata","student2","info","msg");

        //删表
        dropTable(hbaseConnection,"bigdata","student2");

        closeHbaseConnection(hbaseConnection);
    }
}
