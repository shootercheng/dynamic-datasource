package com.scd.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author James
 */
@Data
public class ApiVo {
    @ExcelProperty("Num")
    private Integer num;
    @ExcelProperty("Url")
    private String url;
    @ExcelProperty("Method")
    private String method;
}
