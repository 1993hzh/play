package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Administrator on 2015/6/15.
 */
@Entity
@Table(name = "T_USERROLE")
public class UserRole extends AbstractModel {

    @ManyToOne
    private User user;

    @ManyToOne
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
