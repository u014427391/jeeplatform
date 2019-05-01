package org.muses.jeeplatform.core.dao.repository.admin;


import org.muses.jeeplatform.core.entity.admin.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Nicky on 2017/11/11.
 */
public interface PermissionRepository extends JpaRepository<Permission,Integer> {

}
