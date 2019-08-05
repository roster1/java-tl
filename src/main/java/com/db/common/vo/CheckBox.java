package com.db.common.vo;

import java.io.Serializable;

/**
 * 添加用户时,数据展现,展现用户要选择的角色
 */
public class CheckBox implements Serializable {
    private static final long serialVersionUID = 2031967811425337153L;
    private Integer id;
    private String name;
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
        this.name = name;
    }
    @Override
    public String toString() {
        return "CheckBox [id=" + id + ", name=" + name + "]";
    }
}