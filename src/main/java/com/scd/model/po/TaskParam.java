package com.scd.model.po;

public class TaskParam {
    private Integer id;

    private String paramType;

    private String paramValue;

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
}