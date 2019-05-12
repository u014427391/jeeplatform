package org.muses.jeeplatform.service;

import org.muses.jeeplatform.annotation.LogService;
import org.muses.jeeplatform.core.dao.repository.admin.MenuTreeRepository;
import org.muses.jeeplatform.core.entity.admin.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Nicky on 2017/6/17.
 */
@Service
public class MenuTreeService {

    @Autowired
    MenuTreeRepository menuTreeRepository;

    /**
     * 查询所有的菜单
     * @return
     */
    @Transactional
    //@RedisCache
    @LogService
    public List<Menu> findAll(){
        return menuTreeRepository.findAll();
    }

}
