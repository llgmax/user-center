package com.llg.usercenter.once;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImportExcel {

    public static void main(String[] args){
        String fileName = "D:\\IDE\\workspace\\user-center\\src\\main\\resources\\test.xlsx";
//        simpleRead(fileName);
        synchronousRead(fileName);
    }



    /**
     * 监听器读取数据
     */
    public static void simpleRead(String fileName) {
        // 写法1：JDK8+
        EasyExcel.read(fileName, DemoData.class, new TableListener()).sheet().doRead();

    }

    /**
     * 同步读取
     */
    public static void synchronousRead(String fileName) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<DemoData> list = EasyExcel.read(fileName).head(DemoData.class).sheet().doReadSync();
        System.out.println("总数 :"+list.size());

        Map<String, List<DemoData>> listMap =
                list.stream()
                        .filter(dataInfo -> StringUtils.isNotEmpty(dataInfo.getService()))
                        .collect(Collectors.groupingBy(DemoData::getService));
        System.out.println("分组过滤后总数 :"+listMap.keySet().size());
        for (Map.Entry<String,List<DemoData>> stringListEntry:listMap.entrySet()) {
            if(stringListEntry.getValue().size()>1){
                System.out.println("service = "+stringListEntry.getKey());
            }
        }
    }
}
