package org.muses.jeeplatform.web.controller;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import org.muses.jeeplatform.model.entity.Menu;
import org.muses.jeeplatform.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by op.43027 on 2017/5/27 0027.
 */
@RequestMapping("/menu")
@Controller
public class MenuController extends BaseController {

    @Autowired
    MenuService menuService;

    /**
     * 菜单主页
     *
     * @return
     */
    @RequestMapping(value = "/getMenus", produces = "text/html;charset=UTF-8")
    public ModelAndView toMenuList() {
        List<Menu> menuList = menuService.findAll();

        for (Menu m : menuList) {
            if(m.getParentId()==0){
                m.setHasSubMenu(true);
            }
        }

        ModelAndView mv = this.getModelAndView();
        JSONArray jsonArray = JSONArray.fromObject(menuList);
        String json = jsonArray.toString();

        json = json.replaceAll("menuId","id").replaceAll("parentId","pId").
                replaceAll("menuName","name").replaceAll("hasSubMenu","open");

        mv.addObject("menus", json);
        mv.setViewName("admin/menu/menu_list");
        return mv;
    }

}