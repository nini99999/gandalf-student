package com.poshist.sys.vo;

import com.poshist.sys.entity.Role;

public class RoleVO {
    private Long id;
    private String name;
    private String memo;
    public RoleVO (Role role){
        this.id=role.getId();
        this.name=role.getName();
        this.memo=role.getMemo();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
