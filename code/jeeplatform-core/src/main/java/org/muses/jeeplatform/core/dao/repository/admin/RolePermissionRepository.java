package org.muses.jeeplatform.core.dao.repository.admin;

import org.muses.jeeplatform.core.entity.admin.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Nicky on 2017/11/18.
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission,String> {

    @Query(value = "select rp from RolePermission rp where rp.roleId=:id")
    public List<RolePermission> findByRoleId(@Param("id") int id);

}
