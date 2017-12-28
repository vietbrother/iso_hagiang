package com.iso.dashboard.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "c_menu")
public class CMenu implements Serializable {

    @Id
//    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_menu_id", nullable = false)
    private Integer id;

    @Column(name = "CODE", nullable = false)
    private String code;
    @Column(name = "NAME", nullable = true)
    private String name;
    @Column(name = "PARENT_ID", nullable = true)
    private Integer parentId;
    @Column(name = "IS_ACTIVE", nullable = true)
    private Integer isActive;
    @Column(name = "IS_PARENT", nullable = true)
    private Integer isParent;
    @Column(name = "IS_CONTAIN_CHILD", nullable = true)
    private Integer isContainChild;
    

    @Column(name = "CREATE_TIME", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createTime;

    public CMenu() {
        //contructor
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsParent() {
        return isParent;
    }

    public void setIsParent(Integer isParent) {
        this.isParent = isParent;
    }

    public Integer getIsContainChild() {
        return isContainChild;
    }

    public void setIsContainChild(Integer isContainChild) {
        this.isContainChild = isContainChild;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    

}
