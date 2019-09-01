package com.scd.constant;

/**
 * @author chengdu
 * @date 2019/8/30.
 */
public enum TypeEnum {

    String("java.lang.String"),
    Integer("java.lang.Integer"),
    Long("java.lang.Long"),
    Double("java.lang.Double"),
    Date("java.util.Date");

    private String clazz;

    TypeEnum(String clazz){
        this.clazz = clazz;
    }

    public String type(){
        return clazz;
    }
}
