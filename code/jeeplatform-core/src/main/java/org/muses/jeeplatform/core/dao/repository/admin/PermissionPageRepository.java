package org.muses.jeeplatform.core.dao.repository.admin;


import org.muses.jeeplatform.core.entity.admin.Permission;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Nicky on 2017/12/3.
 */
public interface PermissionPageRepository extends PagingAndSortingRepository<Permission,Integer> {

}
