package org.muses.jeeplatform.service;

import org.muses.jeeplatform.core.dao.repository.admin.RolePermissionRepository;
import org.muses.jeeplatform.core.entity.admin.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Nicky on 2017/11/18.
 */
@Service
public class RolePermissionService {

    @Autowired
    RolePermissionRepository rolePermissionRepository;

    /**
     * 保存数据
     * @param rp
     * @return
     */
    public boolean doSave(RolePermission rp){
        rolePermissionRepository.saveAndFlush(rp);
        return true;
    }

    /**
     * 删除数据
     * @param rp
     * @return
     */
    public boolean doDel(RolePermission rp){
        rolePermissionRepository.delete(rp);
        return true;
    }

    /**
     * 通过角色Id获取数据
     * @param roleId
     * @return
     */
    public List<RolePermission> findById(int roleId){
        List<RolePermission> rp =rolePermissionRepository.findByRoleId(roleId);
        return rp;
    }


}
