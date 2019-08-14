package com.imooc.ad.service;


import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BinlogService {
    public static void main(String[] args) throws Exception{
        BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3306, "root", "root");
        client.setBinlogFilename("binlog.000002");
        client.registerEventListener(event -> {
            EventData data = event.getData();
            EventHeader header = event.getHeader();

            if(data instanceof UpdateRowsEventData){
                System.out.println("Update------------");
                System.out.println(getDate(header.getTimestamp()));
                System.out.println(header.toString());
                System.out.println(data.toString());
            }else if(data instanceof WriteRowsEventData){
                System.out.println("Write-------------");
                System.out.println(getDate(header.getTimestamp()));
                System.out.println(header.toString());
                System.out.println(data.toString());
            }else if(data instanceof DeleteRowsEventData){
                System.out.println("Delete-------------");
                System.out.println(getDate(header.getTimestamp()));
                System.out.println(header.toString());
                System.out.println(data.toString());
            }
        });



        client.connect();

    }

    public static String getDate(long time){
        Date date = new Date(time);
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(date);
        
    }
}
