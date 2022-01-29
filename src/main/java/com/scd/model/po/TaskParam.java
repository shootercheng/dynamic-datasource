package com.scd.model.po;

import java.util.List;

public class TaskParam {
    private Integer id;

    private String paramType;

    private String paramValue;

    private String ids;

    private List<Integer> idList;

    public TaskParam(){}

    public TaskParam(Integer id, String paramType, String paramValue) {
        this.id = id;
        this.paramType = paramType;
        this.paramValue = paramValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType == null ? null : paramType.trim();
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }
}