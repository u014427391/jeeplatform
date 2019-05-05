package org.muses.jeeplatform.service;


import org.muses.jeeplatform.core.dao.repository.admin.PermissionRepository;
import org.muses.jeeplatform.core.entity.admin.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Nicky on 2017/11/11.
 */
@Service
public class PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    /**
     * 保存权限表信息
     *
     * @param permission
     */
    public void doSave(Permission permission) {
        permissionRepository.save(permission);
    }

    //@RedisCache
    public Set<Permission> findAllP() {
        List<Permission> list = permissionRepository.findAll();
        Set<Permission> set = new HashSet<Permission>();
        set.addAll(list);
        list.clear();//清空list，不然下次把set元素加入此list的时候是在原来的基础上追加元素的
        return set;
    }

    public Permission getOne(int id) {
        return this.permissionRepository.getOne(id);
    }

}