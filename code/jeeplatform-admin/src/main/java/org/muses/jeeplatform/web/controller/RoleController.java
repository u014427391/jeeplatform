package org.muses.jeeplatform.web.controller;

import com.alibaba.fastjson.JSON;
import org.muses.jeeplatform.core.CommonConsts;
import org.muses.jeeplatform.core.entity.admin.Menu;
import org.muses.jeeplatform.core.entity.admin.Permission;
import org.muses.jeeplatform.core.entity.admin.Role;
import org.muses.jeeplatform.core.entity.admin.RolePermission;
import org.muses.jeeplatform.service.*;
import org.muses.jeeplatform.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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

        int pageSize = CommonConsts.PAGE_SIZE;
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
    public Map<String,String> addR(@RequestParam("params")String params, HttpServletResponse response){

        String roleName="", roleDesc="";
        String[] strs = StringUtils.split(params, ",");
        roleName = strs[0];
        roleDesc = strs[1];
        //log.info("角色名称:{},角色描述:{}",roleName,roleDesc);
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleDesc(roleDesc);
        role.setRole("role");

        Map<String,String> result = new HashMap<String,String>();
        try {
            roleService.doSave(role);
            result.put("result","success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result","error");
        }
        return result;
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
    @PostMapping(value = "/editR")
    @ResponseBody
    public Map<String,String> editR(@RequestParam("params")String params){
        String strs[]=params.split(",");
        String roleId = strs[0];
        String roleName = strs[1];
        String roleDesc = strs[2];
        Role role = new Role();
        role.setRoleId(Integer.parseInt(roleId));
        role.setRoleName(roleName);
        role.setRoleDesc(roleDesc);
        role.setRole("role");

        Map<String,String> result = new HashMap<String,String>();
        try {
            roleService.doSave(role);
            result.put("result","success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result","error");
        }
        return result;
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

        json = json.replaceAll("menuId","id").replaceAll("parentId","pId").replaceAll("menuName","name").replaceAll("hasSubMenu","checked");

        model.addAttribute("menus",json);

        return "admin/role/role_auth";
    }

    /**
     * 角色授权
     */
    @PostMapping(value = "/authorise", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String,String> authorise(@RequestParam("params")String params){

        String[] strs = params.split(";");

        String roleId = strs[0];
        String menuIdarr = strs[1];
        String[] menuIds = menuIdarr.split(",");

        Map<String,String> result = new HashMap<String,String>();
        try {
            List<RolePermission> rplist = new ArrayList<RolePermission>();
            rplist = rolePermissionService.findById(Integer.parseInt(roleId));
            //先删除数据
            for(RolePermission r:rplist){
                rolePermissionService.doDel(r);
            }

            for (int i=0;i<menuIds.length;i++){
                //重新写入数据
                RolePermission rop = new RolePermission();
                rop.setRpId(UUIDGenerator.getRandomNum());
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

            result.put("result","success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result","error");
        }
        return result;
    }


}