package com.llg.usercenter.once;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode
public class DemoData {
    @ExcelProperty("服务")
    private String service;
    @ExcelProperty("IP")
    private String ip;
    @ExcelProperty("CPU")
    private String cpu;
    @ExcelProperty("内存")
    private String memory;
}