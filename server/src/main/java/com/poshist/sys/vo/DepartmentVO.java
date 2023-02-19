package com.poshist.sys.vo;

import com.poshist.sys.entity.Department;

import java.util.List;

public class DepartmentVO {
    private Long id;
    private String name;
    private String memo;
    private String code;
    private Long parentId;
    private List<DepartmentVO> child;
    public DepartmentVO(Department department){
        this.id=department.getId();
        this.name=department.getName();
        this.parentId=department.getParentDepartment().getId();
        this.memo=department.getMemo();
        this.code=department.getCode();
    }
    public DepartmentVO(){

    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<DepartmentVO> getChild() {
        return child;
    }

    public void setChild(List<DepartmentVO> child) {
        this.child = child;
    }
    public void toDTO(Department department){
        department.setCode(getCode());
        department.setMemo(getMemo());
        department.setName(getName());
        department.setId(getId());
    }
}
