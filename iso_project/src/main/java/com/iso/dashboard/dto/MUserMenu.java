package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the m_flow_step database table.
 *
 */
@Entity
@Table(name = "m_user_menu")
@NamedQuery(name = "MUserMenu.findAll", query = "SELECT m FROM MUserMenu m")
public class MUserMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "c_menu_id", nullable = false)
    private CMenu menu;

    public MUserMenu() {
        //contructor
    }

    public MUserMenu(Integer userId, CMenu menu) {
        this.userId = userId;
        this.menu = menu;
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public CMenu getMenu() {
        return menu;
    }

    public void setMenu(CMenu menu) {
        this.menu = menu;
    }


}
