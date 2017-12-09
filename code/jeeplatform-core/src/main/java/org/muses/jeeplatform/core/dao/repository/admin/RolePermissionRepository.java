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

    @Query("from RolePermission where roleId=:id")
    public List<RolePermission> findByRoleId(@Param("id") int id);

}
