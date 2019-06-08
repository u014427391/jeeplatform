package org.muses.jeeplatform.web.controller;

import com.alibaba.fastjson.JSON;
import org.muses.jeeplatform.annotation.LogController;
import org.muses.jeeplatform.core.CommonConsts;
import org.muses.jeeplatform.core.entity.admin.Menu;
import org.muses.jeeplatform.core.entity.admin.Permission;
import org.muses.jeeplatform.service.MenuService;
import org.muses.jeeplatform.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by op.43027 on 2017/5/27 0027.
 */
@RequestMapping("/menu")
@Controller
public class MenuController extends BaseController {

    @Autowired
    MenuService menuService;
    @Autowired
    PermissionService permissionService;

    @RequestMapping(value = "/getMenus", produces = "application/json;charset=UTF-8")
    @LogController
    public ModelAndView toMenuList(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String pageIndexStr = request.getParameter("pageIndex");

        int pageSize = CommonConsts.PAGE_SIZE;
        ModelAndView mv = this.getModelAndView();
        Page<Menu> menuPage;

        if(pageIndexStr==null||"".equals(pageIndexStr)){
            pageIndexStr = "0";
        }

        int pageIndex = Integer.parseInt(pageIndexStr);

        menuPage = menuService.findAll(pageIndex+1, pageSize, Sort.Direction.ASC,"menuId");
        mv.addObject("totalCount",menuPage.getTotalElements());
        mv.addObject("pageIndex",pageIndex);
        String json = JSON.toJSONString(menuPage.getContent());
        mv.addObject("menus",json);
        mv.setViewName("admin/menu/menu_list");
        return mv;
    }

    @RequestMapping(value = "/loadMenus", produces = "application/json;charset=UTF-8")
    @ResponseBody
    //@LogController
    public String doLoadData(HttpServletRequest request, Model model) throws IOException {
        String pageIndexStr = request.getParameter("pageIndex");
        if(pageIndexStr==null||"".equals(pageIndexStr)){
            pageIndexStr = "1";
        }
        //System.out.println(pageString);
        int pageIndex = Integer.parseInt(pageIndexStr);
        if(pageIndex == 0){
            pageIndex = 1;
        }

        int pageSize = CommonConsts.PAGE_SIZE;
        Page<Menu> menuPage = menuService.findAll(pageIndex, pageSize, Sort.Direction.ASC,"menuId");
        String json = JSON.toJSONString(menuPage.getContent());
        return json;
    }

    @RequestMapping("/list")
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView();
        List<Menu> menus = menuService.findAllParentMenu();
        String json = JSON.toJSONString(menus);
        mv.addObject("menus",json);
        mv.setViewName("admin/menu/menu_list");
        return mv;
    }

    /**
     * 获取当前菜单的所有子菜单
     * @param menuId
    */
    @RequestMapping(value="/sub")
    @ResponseBody
    public String getSub(@RequestParam String menuId)throws Exception{
        String json="";
        try {
            List<Menu> subMenu = menuService.findSubMenuById(Integer.parseInt(menuId));
            json = JSON.toJSONString(subMenu);
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return json;
    }

    /**
     * 跳转到编辑菜单页面
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value="/goEditM")
    public String goEditM(HttpServletRequest request,Model model){
        String menuIdStr = request.getParameter("menuId");
        int menuId = Integer.parseInt(menuIdStr);
        Menu menu = menuService.findMenuById(menuId);
        model.addAttribute("menu",menu);
        return "admin/menu/menu_edit";
    }

    /**
     * 编辑菜单信息
     * @param request
     */
    @PostMapping(value = "/editM")
    @ResponseBody
    public Map<String,String> editM(HttpServletRequest request){
        String params[] = request.getParameter("KEYDATA").split(",");
        String menuId = params[0];
        String parentId = params[1];
        String menuName = params[2];
        String menuUrl = params[3];
        String menuType = params[4];
        String menuOrder = params[5];
        String menuStatus = params[6];
        Menu m = new Menu();
        m.setMenuId(Integer.parseInt(menuId));
        m.setParentId(Integer.parseInt(parentId));
        m.setMenuName(menuName);
        m.setMenuUrl(menuUrl);
        m.setMenuType(menuType);
        m.setMenuOrder(menuOrder);
        m.setMenuIcon("&#xe610");
        m.setMenuStatus(menuStatus);
        Map<String,String> result = new HashMap<String,String>();
        try{
            menuService.editM(m);
            result.put("result","success");
        }catch (Exception e){
            result.put("result","error");
        }
        return result;
    }

    /**
     * 跳转到新增菜单页面
     * @return
     */
    @GetMapping(value="/goAddM")
    public String goAddM(Model model){
        List<Menu> sjMenus = menuService.findAllParentMenu();
        model.addAttribute("sjMenus",sjMenus);
        return "admin/menu/menu_add";
    }

    /**
     * 保存菜单信息
     * @param request
     */
    @PostMapping(value = "/addM")
    @ResponseBody
    public Map<String,String> addM(HttpServletRequest request){
        String[] params = request.getParameter("params").split(",");
        String parentId = params[0];
        String menuName = params[1];
        String menuUrl = params[2];
        String menuOrder = params[3];

        Menu menu = new Menu();
        menu.setParentId(Integer.parseInt(parentId));
        menu.setMenuName(menuName);
        menu.setMenuIcon("&#xe610");
        menu.setMenuUrl(menuUrl);
        menu.setMenuType("1");
        menu.setMenuOrder(menuOrder);
        menu.setMenuStatus("1");

        Map<String,String> result = new HashMap<String,String>();
        try {
            Permission p = new Permission();
            p.setMenu(menu);
            permissionService.doSave(p);

            menuService.saveM(menu);

            result.put("result","success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result","error");
        }
        return result;
    }



}