package org.muses.jeeplatform.repository;

import org.muses.jeeplatform.model.entity.Role;
import org.muses.jeeplatform.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Nicky on 2017/7/30.
 */
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {


}
