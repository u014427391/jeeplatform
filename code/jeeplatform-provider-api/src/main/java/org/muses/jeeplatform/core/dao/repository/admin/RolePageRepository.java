package org.muses.jeeplatform.core.dao.repository.admin;

import org.muses.jeeplatform.core.entity.admin.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Nicky on 2017/7/30.
 */
public interface RolePageRepository extends PagingAndSortingRepository<Role, Integer> {

//    @Query("from Role r where r.roleId=:id")
//    Role findByRoleId(@Param("id") int id);



}
