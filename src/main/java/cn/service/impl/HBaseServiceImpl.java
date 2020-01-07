package cn.service.impl;

import cn.mapper.Mapper;
import cn.pojo.Cassandra.Location;
import cn.pojo.Cassandra.Season;
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
        conf.set("hbase.master", "worker03:16000");
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
    @Test
    public List<Season> queryTable() throws IOException {
            getconncet();
            List<Season> p = new ArrayList<>();
            Table table =connection.getTable(TableName.valueOf("ride"));

            for(int i=1;i<=20;i++){
                Get get1 = new Get(Bytes.toBytes(String.valueOf(i)));
                Result result = table.get(get1);
                Season o = new Season();
                o.setId(new String(result.getRow()));
                System.out.println(new String(result.getRow()));
                for (Cell cell : result.rawCells()) {
                    if (new String(CellUtil.cloneQualifier(cell)).equals("Startdate")){
                        o.setStartDate(new String(CellUtil.cloneValue(cell)));
//                        System.out.println(new String(CellUtil.cloneValue(cell)));
                    }
                    if(new String(CellUtil.cloneQualifier(cell)).equals("Startstation")){
                        o.setStartStation(new String(CellUtil.cloneValue(cell)));
//                        System.out.println(new String(CellUtil.cloneValue(cell)));
                    }
                    if(new String(CellUtil.cloneQualifier(cell)).equals("Startterminal")){
                        o.setStartStationNumber(new String(CellUtil.cloneValue(cell)));
//                        System.out.println(new String(CellUtil.cloneValue(cell)));
                    }
                    if(new String(CellUtil.cloneQualifier(cell)).equals("Enddate")){
                        o.setEndDate(new String(CellUtil.cloneValue(cell)));
//                        System.out.println(new String(CellUtil.cloneValue(cell)));
                    }
                    if (new String(CellUtil.cloneQualifier(cell)).equals("Endstation")){
                        o.setEndStation(new String(CellUtil.cloneValue(cell)));
//                        System.out.println(new String(CellUtil.cloneValue(cell)));
                    }
                    if (new String(CellUtil.cloneQualifier(cell)).equals("Endterminal")){
                        o.setEndStationNumber(new String(CellUtil.cloneValue(cell)));
//                        System.out.println(new String(CellUtil.cloneValue(cell)));
                    }
                    if (new String(CellUtil.cloneQualifier(cell)).equals("Totalduration")){
                        o.setTotalDuration(new String(CellUtil.cloneValue(cell)));
//                        System.out.println(new String(CellUtil.cloneValue(cell)));
                    }
                    if (new String(CellUtil.cloneQualifier(cell)).equals("Accounttype")){
                        o.setAccountType(new String(CellUtil.cloneValue(cell)));
//                        System.out.println(new String(CellUtil.cloneValue(cell)));
                    }
                }
//            System.out.println(o.getId()+" "+o.getStartDate()+" "+o.getStartStation()+" "+o.getStartStationNumber()+" "+o.getEndDate()+" "+o.getEndStation()+" "+o.getEndStationNumber()+" "+o.getTotalDuration()+" "+o.getAccountType());
                p.add(o);
            }
            return p;
    }

    //根据行键get查询数据
    @Test
    public List<Season> getData(Season s) throws IOException {

        getconncet();
            List<Season> p = new ArrayList<>();
            Table table_Name = connection.getTable(TableName.valueOf("ride"));
            Get get1 = new Get(Bytes.toBytes(s.getId()));
            Result result = table_Name.get(get1);
            Season o = new Season();
            o.setId(new String(result.getRow()));
            System.out.println(new String(result.getRow()));
            for (Cell cell : result.rawCells()) {
                if (new String(CellUtil.cloneQualifier(cell)).equals("Startdate")){
                    o.setStartDate(new String(CellUtil.cloneValue(cell)));
                        System.out.println(new String(CellUtil.cloneValue(cell)));
                }
                if(new String(CellUtil.cloneQualifier(cell)).equals("Startstation")){
                    o.setStartStation(new String(CellUtil.cloneValue(cell)));
                        System.out.println(new String(CellUtil.cloneValue(cell)));
                }
                if(new String(CellUtil.cloneQualifier(cell)).equals("Startterminal")){
                    o.setStartStationNumber(new String(CellUtil.cloneValue(cell)));
                        System.out.println(new String(CellUtil.cloneValue(cell)));
                }
                if(new String(CellUtil.cloneQualifier(cell)).equals("Enddate")){
                    o.setEndDate(new String(CellUtil.cloneValue(cell)));
                        System.out.println(new String(CellUtil.cloneValue(cell)));
                }
                if (new String(CellUtil.cloneQualifier(cell)).equals("Endstation")){
                    o.setEndStation(new String(CellUtil.cloneValue(cell)));
                    System.out.println(new String(CellUtil.cloneValue(cell)));
                }
                if (new String(CellUtil.cloneQualifier(cell)).equals("Endterminal")){
                    o.setEndStationNumber(new String(CellUtil.cloneValue(cell)));
                    System.out.println(new String(CellUtil.cloneValue(cell)));
                }
                if (new String(CellUtil.cloneQualifier(cell)).equals("Totalduration")){
                    o.setTotalDuration(new String(CellUtil.cloneValue(cell)));
                    System.out.println(new String(CellUtil.cloneValue(cell)));
                }
                if (new String(CellUtil.cloneQualifier(cell)).equals("Accounttype")){
                    o.setAccountType(new String(CellUtil.cloneValue(cell)));
                    System.out.println(new String(CellUtil.cloneValue(cell)));
                }
            }
            System.out.println(o.getId()+" "+o.getStartDate()+" "+o.getStartStation()+" "+o.getStartStationNumber()+" "+o.getEndDate()+" "+o.getEndStation()+" "+o.getEndStationNumber()+" "+o.getTotalDuration()+" "+o.getAccountType());
            p.add(o);
            table_Name.close();
            return p;
    }
//    插入数据
    @Test
    public void addSeasonData(Season o) throws IOException {
            getconncet();
            HTable tableName=(HTable)connection.getTable(TableName.valueOf("ride"));
            //设置客户端缓存大小，即缓存为6MB时执行真正的put，在flushCommits时进行手动提交。若为true，则每次put操作都直接提交给regionserver，在大批量写入时效率较低。
            tableName.setWriteBufferSize(6 * 1024 * 1024);
            tableName.setAutoFlushTo(false);
            //关闭WAL写入
            Put put1 = new Put(Bytes.toBytes(o.getId())); //行键
            put1.setDurability(Durability.SKIP_WAL); //批量加载，关闭预写日志WAL，如果出现问题，可以重新运行批量负载，而不会有数据丢失的风险
            //列族名，列名，值，以下数据的时间戳是相同的
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("Startdate"), Bytes.toBytes(o.getStartDate()));
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("Startstation"), Bytes.toBytes(o.getStartStation()));
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("Startterminal"), Bytes.toBytes(o.getStartStationNumber()));
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("Enddate"), Bytes.toBytes(o.getEndDate()));
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("Endstation"), Bytes.toBytes(o.getEndStation()));
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("Endterminal"), Bytes.toBytes(o.getEndStationNumber()));
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("Totalduration"), Bytes.toBytes(o.getTotalDuration()));
            put1.addColumn(Bytes.toBytes("info"), Bytes.toBytes("Accounttype"), Bytes.toBytes(o.getAccountType()));
            tableName.put(put1);
            tableName.flushCommits();
            System.out.println("添加数据成功");
            tableName.close();

    }
//    删除行数据
    @Test
    public void deleteRow(String... rows) throws IOException {
        try {
            getconncet();
            TableName table_Name = TableName.valueOf("ride");
            HTable table = (HTable) connection.getTable(table_Name);
            for (int i = 0; i < rows.length; i++) {
                Delete delete = new Delete(Bytes.toBytes(rows[i]));
                table.delete(delete);
            }
//            System.out.println("删除行成功");
            table.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("删除行失败");
        }
    }

    @Test
    public void hh() throws IOException {
        List<Season> o= queryTable();
//        createTable("hhh","fff");
    }

    @Test
    public void createtable() throws IOException {
        getconncet();
        TableName tableName =TableName.valueOf("NEWTABLE");
        Admin admin = connection.getAdmin();
        if (admin.tableExists(tableName)){
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println(tableName.toString()+"is exists, delete it..............");
        }
        HTableDescriptor descriptor = new HTableDescriptor(tableName);
        HColumnDescriptor columnDescriptor = new HColumnDescriptor("cf1");
        columnDescriptor.setBloomFilterType(BloomType.ROWCOL);
        descriptor.addFamily(columnDescriptor);
        descriptor.addFamily(new HColumnDescriptor("cf2"));
        admin.createTable(descriptor);
        admin.close();
    }


}
