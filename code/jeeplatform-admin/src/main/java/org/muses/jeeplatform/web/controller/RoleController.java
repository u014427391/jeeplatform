package org.muses.jeeplatform.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.muses.jeeplatform.core.Constants;
import org.muses.jeeplatform.core.entity.admin.Menu;
import org.muses.jeeplatform.core.entity.admin.Permission;
import org.muses.jeeplatform.core.entity.admin.Role;
import org.muses.jeeplatform.core.entity.admin.RolePermission;
import org.muses.jeeplatform.service.*;
import org.muses.jeeplatform.utils.UUIDUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * Created by Nicky on 2017/7/30.
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    RolePageService roleService;
    @Autowired
    MenuService menuService;
    @Autowired
    MenuTreeService menuTreeService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RolePermissionService rolePermissionService;

    /**
     * 查询所有角色信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/queryAll", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryAll(HttpServletRequest request, HttpServletResponse response, Model model){
        String pageIndexStr = request.getParameter("pageIndex");

        int pageSize = Constants.PAGE_SIZE;
        ModelAndView mv = this.getModelAndView();
        Page<Role> rolePage;

        if(pageIndexStr==null||"".equals(pageIndexStr)){
            pageIndexStr = "0";
        }

        int pageIndex = Integer.parseInt(pageIndexStr);

        rolePage = roleService.findAll(pageIndex+1, pageSize, Sort.Direction.ASC,"roleId");
        mv.addObject("totalCount",rolePage.getTotalElements());
        mv.addObject("pageIndex",pageIndex);

       String json = JSON.toJSONString(rolePage.getContent());

        mv.addObject("roles",json);
        mv.setViewName("admin/role/role_list");
        return mv;
    }

    /**
     * 跳转到新增角色页面
     * @return
     */
    @RequestMapping(value = "/goAddR" , method = RequestMethod.GET)
    public String goAddR(){
        return "admin/role/role_add";
    }

    /**
     *  新增角色信息
     * @param params
     * @param response
     */
    @RequestMapping(value = "/addR" , method = RequestMethod.POST)
    @ResponseBody
    public void addR(@RequestParam("params")String params, HttpServletResponse response){
        String[] strs = params.split(",");
        String roleName = strs[0];
        String roleDesc = strs[1];
        System.out.println(roleName+","+roleDesc);
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleDesc(roleDesc);
        role.setRole("role");

        PrintWriter out = null;

        response.setCharacterEncoding("utf-8");

        JSONObject obj = new JSONObject();

        try {
            out = response.getWriter();
            roleService.doSave(role);
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

    /**
     * 跳转到编辑角色信息页面
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping(value = "/goEditR", method = RequestMethod.GET)
    public String goEditR(@RequestParam("roleId")String roleId, Model model){
        Role role = roleService.findByRoleId(roleId);
        model.addAttribute("role",role);
        return "admin/role/role_edit";
    }

    /**
     * 编辑角色信息
     * @param params
     */
    @RequestMapping(value = "/editR", method = RequestMethod.POST)
    @ResponseBody
    public void editR(@RequestParam("params")String params, HttpServletResponse response){
        String strs[]=params.split(",");
        String roleId = strs[0];
        String roleName = strs[1];
        String roleDesc = strs[2];
        Role role = new Role();
        role.setRoleId(Integer.parseInt(roleId));
        role.setRoleName(roleName);
        role.setRoleDesc(roleDesc);
        role.setRole("role");
        PrintWriter out = null;

        response.setCharacterEncoding("utf-8");

        JSONObject obj = new JSONObject();

        try {
            out = response.getWriter();
            roleService.doSave(role);
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

    /**
     * 跳转到角色授权页面
     * @param roleId
     * @param model
     * @return
     */
    @RequestMapping(value = "/goAuthorise" )
    public String goAuth(@RequestParam String roleId, Model model){

        List<Menu> menuList = menuTreeService.findAll();

        Role role = roleService.findByRoleId(roleId);

        Set<Permission> hasPermissions = null;

        if(role != null){
            hasPermissions = role.getPermissions();
        }

        for (Menu m : menuList) {
            for(Permission p : hasPermissions){
                if(p.getMenu().getMenuId()==m.getMenuId()){
                    m.setHasSubMenu(true);
                }
            }
        }

        model.addAttribute("roleId" , roleId);

        String json = JSON.toJSONString(menuList);

        json = json.replaceAll("menuId","id").replaceAll("parentId","pId").
                replaceAll("menuName","name").replaceAll("hasSubMenu","checked");

        model.addAttribute("menus",json);

        return "admin/role/role_auth";
    }

    /**
     * 角色授权
     * @param response
     */
    @RequestMapping(value = "/authorise", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void authorise(@RequestParam("params")String params, HttpServletResponse response){

        String[] strs = params.split(";");

        String roleId = strs[0];
        String menuIdarr = strs[1];
        String[] menuIds = menuIdarr.split(",");

        PrintWriter out = null;

        response.setCharacterEncoding("utf-8");

        JSONObject obj = new JSONObject();

        try {
            out = response.getWriter();
            List<RolePermission> rplist = new ArrayList<RolePermission>();
            rplist = rolePermissionService.findById(Integer.parseInt(roleId));
            //先删除数据
            for(RolePermission r:rplist){
                rolePermissionService.doDel(r);
            }

            for (int i=0;i<menuIds.length;i++){
                //重新写入数据
                RolePermission rop = new RolePermission();
                rop.setRpId(UUIDUtil.getRandomNum());
                rop.setRoleId(Integer.parseInt(roleId));
                rop.setPermissionId(Integer.parseInt(menuIds[i]));
                rolePermissionService.doSave(rop);
            }

//            Role role = roleService.findByRoleId(roleId);
//            Set<Permission> permissions = role.getPermissions();
//
//            Set<Permission> hasPerms = role.getPermissions();
//
//            for(Permission p:permissions){
//                if(hasPerms.contains(p))
//                    continue;
//                role.getPermissions().add(p);
//            }
//
//            roleService.doSave(role);

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

//    public void delR(List<RolePermission> rplist){
//
//    }

}