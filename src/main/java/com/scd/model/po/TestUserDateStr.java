package com.scd.model.po;

import java.time.LocalDateTime;

/**
 * @author James
 */
public class TestUserDateStr {
    private Integer id;

    private String name;

    private String address;

    private String createTime;

    public TestUserDateStr() {

    }

    public TestUserDateStr(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TestUserDateStr{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
