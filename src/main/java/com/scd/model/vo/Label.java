package com.scd.model.vo;

import lombok.Data;
import org.osource.scd.anno.Location;

import java.sql.Date;

@Data
public class Label {
    @Location(column = "A")
    private Long id;

    @Location(column = "E")
    private int type;

    @Location(column = "C")
    private String code;

    @Location(column = "D")
    private int status;

    @Location(column = "B")
    private Long parent;

    private Date date;

    @Location(column = "I")
    private Long userId;

    @Location(column = "F")
    private Long customerId;

    private Long productId;
    private int queryTimes;
    private Date firstTime;

    @Location(column = "N")
    private Date createTime;

    @Location(column = "O")
    private Date updateTime;

    @Location(column = "G")
    private Long operatorId;

    @Location(column = "H")
    private Long resellerId;

    @Location(column = "K")
    private int batch;
}
