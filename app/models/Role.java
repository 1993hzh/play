package models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2015/6/15.
 */
@Entity
@Table(name = "T_ROLE")
public class Role extends AbstractModel {

    private String roleName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
