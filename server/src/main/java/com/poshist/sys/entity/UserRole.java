package com.poshist.sys.entity;

import com.poshist.common.entity.AbstractEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "T_SYS_USER_ROLE")
@EntityListeners(AuditingEntityListener.class)
public class UserRole extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @OneToOne
    @JoinColumn(name = "ROLE_ID",referencedColumnName = "id")
    private Role role;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
