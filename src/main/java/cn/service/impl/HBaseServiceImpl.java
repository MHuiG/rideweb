package cn.service.impl;

import cn.mapper.Mapper;
import cn.service.HBaseService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

@org.springframework.stereotype.Service
public class HBaseServiceImpl implements HBaseService {

    @Autowired
    private Mapper mapper;
    public static Configuration conf;
    public static Connection connection;
    public static Admin admin;
    private Table table = null;
    @Test
    public void init() throws Exception {
        conf = HBaseConfiguration.create();// 配置
        conf.set("hbase.zookeeper.quorum", "worker03");// zookeeper地址
        conf.set("hbase.zookeeper.property.clientPort", "2181");// zookeeper端口
        connection = ConnectionFactory.createConnection(conf);
        table = connection.getTable(TableName.valueOf("wfc"));
        System.out.println("连接成功");
//        Scan scan = new Scan();
//        ResultScanner rss = table.getScanner(scan);
//        for(Result r:rss){
//            System.out.println("\n row: "+new String(r.getRow()));
//        }
    }
//    @Test
//    public void main(){
//            conf = HBaseConfiguration.create();
//            conf.set("hbase.rootdir", "hdfs://worker03:8020/HBase");
//            conf.set("hbase.zookeeper.quorum", "worker03");
//            conf.set("hbase.zookeeper.property.clientPort", "2181");// zookeeper端口
//            try {
//                connection = ConnectionFactory.createConnection(conf);
//                admin = connection.getAdmin();
//                Table table_Name = connection.getTable(TableName.valueOf("ride"));
//                Put put1 = new Put(Bytes.toBytes("005")); //行键
//                put1.setDurability(Durability.SKIP_WAL); //批量加载，关闭预写日志WAL，如果出现问题，可以重新运行批量负载，而不会有数据丢失的风险
//                //列族名，列名，值，以下数据的时间戳是相同的
//                put1.add(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("wfc"));
//                put1.add(Bytes.toBytes("info"), Bytes.toBytes("age"), Bytes.toBytes("21"));
//                put1.add(Bytes.toBytes("info"), Bytes.toBytes("sex"), Bytes.toBytes("男"));
//                table_Name.put(put1);
//                table_Name.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.printf("11111!");
//    }

}
