package cn.service.impl;

import cn.mapper.Mapper;
import cn.pojo.Cassandra.Location;
import cn.service.HBaseService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.regionserver.BloomType;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.sql.ResultSet;
import java.util.*;
import java.util.logging.Filter;
@org.springframework.stereotype.Service
public class HBaseServiceImpl implements HBaseService {

    @Autowired
    private Mapper mapper;
    public static Configuration conf;
    public static Connection connection;
//    建立连接
    @Test
    public void getconncet(){
        conf= HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","worker03");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.set("zookeeper.znode.parent","/hbase");
        try{
            connection=ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    创建新表
    @Test
    public void createTable(String tableName, String... columnFamily) throws IOException {
        try {
            getconncet();
            Admin admin = connection.getAdmin();
            TableName table_Name = TableName.valueOf(tableName);
            if (admin.tableExists(table_Name)) {
                System.out.println("表" + table_Name + "已经存在了!!!");
            } else {
                HTableDescriptor tb = new HTableDescriptor(table_Name);
                for (int i = 0; i < columnFamily.length; i++) {
                    HColumnDescriptor family = new HColumnDescriptor(columnFamily[i]);
                    tb.addFamily(family);
                }
                admin.createTable(tb);
                System.out.println("表"+table_Name + "创建成功");
                admin.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("表"+tableName + "创建失败");
        }
    }
//    删除表
    @Test
    public void deleteTable(String... tableNames) throws IOException {
        try {
            getconncet();
            Admin admin = connection.getAdmin();
            for(int i=0;i<tableNames.length;i++){
                TableName table_Name = TableName.valueOf(tableNames[i]);
                if (admin.tableExists(table_Name)) {
                    admin.disableTable(table_Name);
                    admin.deleteTable(table_Name);
                    System.out.println("表"+table_Name + "删除成功");
                } else {
                    System.out.println("表"+table_Name + "不存在!!!");
                }
            }
            admin.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("删除表" + tableNames + "失败");
        }
    }
// 查询整表

    public void queryTable(String tableName) throws IOException {
        try{
            getconncet();
            List<Location> p = new ArrayList<>();
            Table table =connection.getTable(TableName.valueOf(tableName));
            Scan scan=new Scan();
            ResultScanner results=table.getScanner(scan);
            for (Result result:results){
                Location o = new Location();
                System.out.println(new String(result.getRow()));
                o.setTerminal(new String(result.getRow()));
                for (Cell cell:result.rawCells()){
                    //列名
                    if (new String(CellUtil.cloneQualifier(cell)).equals("Latitude")){
                        o.setLatitude(new String(CellUtil.cloneValue(cell)));
//                        System.out.println(new String(CellUtil.cloneValue(cell)));
                    }
                    if(new String(CellUtil.cloneQualifier(cell)).equals("Longitude")){
                        o.setLongitude(new String(CellUtil.cloneValue(cell)));
//                        System.out.println(new String(CellUtil.cloneValue(cell)));
                    }
                    if(new String(CellUtil.cloneQualifier(cell)).equals("Station")){
                        o.setStation(new String(CellUtil.cloneValue(cell)));
//                        System.out.println(new String(CellUtil.cloneValue(cell)));
                    }
                    if(new String(CellUtil.cloneQualifier(cell)).equals("Nbdocks")){
                        o.setNbdocks(new String(CellUtil.cloneValue(cell)));
//                        System.out.println(new String(CellUtil.cloneValue(cell)));
                    }
                }
                System.out.println("**************************");
                System.out.println(""+o.getTerminal()+o.getStation()+o.getLatitude()+o.getLongitude()+o.getNbdocks());
                System.out.println("**************************");
                p.add(o);
            }

            System.out.println(p.toString());
         System.out.println("---------------查询整表数据结束----------");
        }catch (IOException e) {
                         e.printStackTrace();
                     System.out.println("查询失败");
            }
    }

    //get查询数据
    @Test
    public void getData(String tableName, String row) throws IOException {
        try {
            getconncet();
            Table table_Name = connection.getTable(TableName.valueOf(tableName));
            Get get1 = new Get(Bytes.toBytes(row));
            Result result = table_Name.get(get1);
            for (Cell cell : result.rawCells()) {
                //分别是cell、列族名、列名、值、键值对的时间戳
                System.out.println("cell是"+new String(CellUtil.getCellKeyAsString(cell)) + " \t"+"列族名是" + new String(CellUtil.cloneFamily(cell))
                        + " \t"+"列名是" + new String(CellUtil.cloneQualifier(cell)) + " \t"+"值和时间戳分别是" + new String(CellUtil.cloneValue(cell)) + ":" + cell.getTimestamp());

            }
            table_Name.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("get方法查询表失败");
        }
    }
//    插入数据
    @Test
    public void addData(String tableName,String Terminal,String Station,String Latitude,String Longitude,String Nbdocks) throws IOException {
        try {
            getconncet();
            HTable table_Name = (HTable) connection.getTable(TableName.valueOf(tableName));
            //设置客户端缓存大小，即缓存为6MB时执行真正的put，在flushCommits时进行手动提交。若为true，则每次put操作都直接提交给regionserver，在大批量写入时效率较低。
            table_Name.setWriteBufferSize(6 * 1024 * 1024);
            table_Name.setAutoFlushTo(false);
            //关闭WAL写入
            Put put1 = new Put(Bytes.toBytes(Terminal)); //行键
            put1.setDurability(Durability.SKIP_WAL); //批量加载，关闭预写日志WAL，如果出现问题，可以重新运行批量负载，而不会有数据丢失的风险
            //列族名，列名，值，以下数据的时间戳是相同的
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("Latitude"), Bytes.toBytes(Latitude));
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("Longitude"), Bytes.toBytes(Longitude));
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("Station"), Bytes.toBytes(Station));
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("Nbdocks"), Bytes.toBytes(Nbdocks));
            table_Name.put(put1);
            table_Name.flushCommits();
            System.out.println("添加数据成功");
            table_Name.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("添加数据失败");
        }
    }
//    删除行数据
    @Test
    public void deleteRow(String tableName, String... rows) throws IOException {
        try {
            getconncet();
            TableName table_Name = TableName.valueOf(tableName);
            HTable table = (HTable) connection.getTable(table_Name);
            for (int i = 0; i < rows.length; i++) {
                Delete delete = new Delete(Bytes.toBytes(rows[i]));
                table.delete(delete);
            }
            System.out.println("删除行成功");
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("删除行失败");
        }
    }
//    修改数据
    @Test
    public void updatedata(String tableName,String Terminal,String row,String data) throws IOException{
        try{
            getconncet();
            HTable table_Name = (HTable) connection.getTable(TableName.valueOf(tableName));
            //设置客户端缓存大小，即缓存为6MB时执行真正的put，在flushCommits时进行手动提交。若为true，则每次put操作都直接提交给regionserver，在大批量写入时效率较低。
            table_Name.setWriteBufferSize(6 * 1024 * 1024);
            table_Name.setAutoFlushTo(false);
            Put put1 = new Put(Bytes.toBytes(Terminal));
            //Updating
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes(row),Bytes.toBytes(data));
            //save
            table_Name.put(put1);
            table_Name.flushCommits();
            System.out.println("data Updated!");
            table_Name.close();
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("修改行失败");
        }
    }

    @Test
    public void hh(){
        try{
//            deleteRow("locations","4000") ;
//            addData("locations","4000","wfc","400","500","50");
//            updatedata("locations","4000","Station","www");
//            getData("locations","4000");
            queryTable("locations");
        }catch (IOException e){
            e.printStackTrace();
        }
    }



}
