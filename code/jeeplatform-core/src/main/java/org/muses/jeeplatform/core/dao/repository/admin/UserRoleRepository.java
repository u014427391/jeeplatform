package org.muses.jeeplatform.core.dao.repository.admin;

import org.muses.jeeplatform.core.entity.admin.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nicky on 2017/12/2.
 */
public interface UserRoleRepository extends JpaRepository<RolePermission,String> {
}
