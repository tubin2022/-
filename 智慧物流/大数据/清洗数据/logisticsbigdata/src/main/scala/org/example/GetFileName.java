package org.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

public class GetFileName {
    public String getName() {
        //获取当前日期的前一天
        LocalDate localDate = LocalDate.now().minusDays(1);
//        System.out.println(localDate.toString());
        //获取当前年
//        System.out.println(localDate.getYear());
        int year = localDate.getYear();
        //获取当前月
//        System.out.println(localDate.getMonthValue());
        int monthValue = localDate.getMonthValue();
        String month;
        if (monthValue < 10) {
            month = "0" + monthValue;
        } else {
            month = Integer.valueOf(monthValue).toString();
        }
        //获取当前天
//        System.out.println(localDate.getDayOfMonth());
        int dayOfMonth = localDate.getDayOfMonth();
        Configuration conf = new Configuration();
        FileSystem fs = null;
        String path =null;
        try {
            fs = FileSystem.newInstance(new URI("hdfs://master:8020"), conf);
            Path p = new Path("/user/flume/logistics/" + year + "/" + month + "-" + dayOfMonth + "/");
            RemoteIterator<LocatedFileStatus> rs = fs.listFiles(p, true);
            while (rs.hasNext()) {
                LocatedFileStatus lf = rs.next();
                System.out.println(lf.getPath().toString());
                path =  lf.getPath().toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }finally {
            return path;
        }
    }
}
