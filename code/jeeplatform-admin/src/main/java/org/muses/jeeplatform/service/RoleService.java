package org.muses.jeeplatform.service;

import org.muses.jeeplatform.annotation.RedisCache;
import org.muses.jeeplatform.common.RedisCacheNamespace;
import org.muses.jeeplatform.core.dao.repository.admin.RoleRepository;
import org.muses.jeeplatform.core.entity.admin.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nicky on 2017/12/2.
 */
@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    /**
     *
     * @param ids
     * @return
     */
    //@RedisCache
    public List<Role> findAll(List<Integer> ids){
        return roleRepository.findAll(ids);
    }
}
