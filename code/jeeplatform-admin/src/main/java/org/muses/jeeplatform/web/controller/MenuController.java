package org.muses.jeeplatform.web.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.muses.jeeplatform.core.Constants;
import org.muses.jeeplatform.core.entity.admin.Menu;
import org.muses.jeeplatform.core.entity.admin.Permission;
import org.muses.jeeplatform.service.MenuService;
import org.muses.jeeplatform.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
    @ResponseBody
    public ModelAndView toMenuList(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String pageIndexStr = request.getParameter("pageIndex");

        int pageSize = Constants.PAGE_SIZE;
        ModelAndView mv = this.getModelAndView();
        Page<Menu> menuPage;

        if(pageIndexStr==null||"".equals(pageIndexStr)){
            pageIndexStr = "0";
        }

        int pageIndex = Integer.parseInt(pageIndexStr);

        menuPage = menuService.findAll(pageIndex+1, pageSize, Sort.Direction.ASC,"menuId");
        mv.addObject("totalCount",menuPage.getTotalElements());
        mv.addObject("pageIndex",pageIndex);
        JSONArray jsonData = JSONArray.fromObject(menuPage.getContent());
        mv.addObject("menus",jsonData.toString());

        mv.setViewName("admin/menu/menu_list");
        return mv;
    }

    @RequestMapping(value = "/loadMenus", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void doLoadData(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        String pageIndexStr = request.getParameter("pageIndex");
        if(pageIndexStr==null||"".equals(pageIndexStr)){
            pageIndexStr = "1";
        }
        //System.out.println(pageString);
        int pageIndex = Integer.parseInt(pageIndexStr);
        if(pageIndex == 0){
            pageIndex = 1;
        }

        int pageSize = Constants.PAGE_SIZE;
        Page<Menu> menuPage = menuService.findAll(pageIndex, pageSize, Sort.Direction.ASC,"menuId");
        JSONArray jsonData = JSONArray.fromObject(menuPage.getContent());

        PrintWriter out;

        response.setCharacterEncoding("utf-8");
        out = response.getWriter();
        out.write(jsonData.toString());
        out.flush();
        out.close();

    }

    @RequestMapping("/list")
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView();
        List<Menu> menus = menuService.findAllParentMenu();
        JSONArray jsonObject = JSONArray.fromObject(menus);
        mv.addObject("menus",jsonObject.toString());
        mv.setViewName("admin/menu/menu_list");
        return mv;
    }

    /**
     * 获取当前菜单的所有子菜单
     * @param menuId
     * @param response
     */
    @RequestMapping(value="/sub")
    public void getSub(@RequestParam String menuId, HttpServletResponse response)throws Exception{
        try {
            List<Menu> subMenu = menuService.findSubMenuById(Integer.parseInt(menuId));
            JSONArray arr = JSONArray.fromObject(subMenu);
            PrintWriter out;

            response.setCharacterEncoding("utf-8");
            out = response.getWriter();
            String json = arr.toString();
            out.write(json);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
    }

    /**
     * 跳转到编辑菜单页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="/goEditM",method = RequestMethod.GET)
    public String goEditM(HttpServletRequest request,Model model){
        String menuIdStr = request.getParameter("menuId");
        int menuId = Integer.parseInt(menuIdStr);
        Menu menu = menuService.findMenuById(menuId);
        model.addAttribute("menu",menu);
        return "admin/menu/menu_edit";
    }

    /**
     * 编辑菜单信息
     * @param response
     * @param request
     */
    @RequestMapping(value = "/editM", method = RequestMethod.POST)
    public void editM(HttpServletResponse response,HttpServletRequest request){
        PrintWriter out = null;

        response.setCharacterEncoding("utf-8");
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        menuService.editM(m);

        JSONObject obj = new JSONObject();
        obj.put("result","success");
        out.write(obj.toString());
        out.flush();
        out.close();
    }

    /**
     * 跳转到新增菜单页面
     * @return
     */
    @RequestMapping(value="/goAddM",method=RequestMethod.GET)
    public String goAddM(Model model){
        List<Menu> sjMenus = menuService.findAllParentMenu();
        model.addAttribute("sjMenus",sjMenus);
        return "admin/menu/menu_add";
    }

    /**
     * 保存菜单信息
     * @param request
     * @param response
     */
    @RequestMapping(value = "/addM", method = RequestMethod.POST)
    @ResponseBody
    public void addM(HttpServletRequest request, HttpServletResponse response){
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
        PrintWriter out = null;

        response.setCharacterEncoding("utf-8");

        JSONObject obj = new JSONObject();

        try {
            out = response.getWriter();
            Permission p = new Permission();
            p.setMenu(menu);
            permissionService.doSave(p);

            menuService.saveM(menu);

            obj.put("result","success");
            out.write(obj.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            obj.put("result","error");
            out.write(obj.toString());
            out.flush();
            out.close();
        }
    }



}