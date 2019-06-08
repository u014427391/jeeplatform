package org.muses.jeeplatform.web.controller;

import com.alibaba.fastjson.JSON;
import org.muses.jeeplatform.annotation.LogController;
import org.muses.jeeplatform.core.CommonConsts;
import org.muses.jeeplatform.core.entity.admin.Permission;
import org.muses.jeeplatform.service.PermissionPageService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nicky on 2017/12/3.
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    @Autowired
    PermissionPageService permissionPageService;
    @Autowired
    PermissionService permissionService;

    /**
     * 查询所有权限信息
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping(value = "/queryAll", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryAll(HttpServletRequest request, HttpServletResponse response, Model model){
        String pageIndexStr = request.getParameter("pageIndex");

        int pageSize = CommonConsts.PAGE_SIZE;
        ModelAndView mv = this.getModelAndView();
        Page<Permission> permissionPage;

        if(pageIndexStr==null||"".equals(pageIndexStr)){
            pageIndexStr = "0";
        }

        int pageIndex = Integer.parseInt(pageIndexStr);

        permissionPage = permissionPageService.findAll(pageIndex+1, pageSize, Sort.Direction.ASC,"id");
        mv.addObject("totalCount",permissionPage.getTotalElements());
        mv.addObject("pageIndex",pageIndex);

        //JSONArray jsonArray = JSONArray.fromObject(permissionPage.getContent());
        String json = JSON.toJSONString(permissionPage.getContent());
        mv.addObject("permissions",json);
        mv.setViewName("admin/permission/permission_list");
        return mv;
    }

    /**
     * 跳转到编辑权限信息页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/goEditP", method = RequestMethod.GET)
    public String goEditP(@RequestParam("pId")String pId, Model model){
        Permission permission = this.permissionService.getOne(Integer.parseInt(pId));
        model.addAttribute("permission" , permission);
        return "admin/permission/permission_edit";
    }

    /**
     * 编辑权限信息
     * @param params
     */
    @PostMapping(value = "/editP")
    @ResponseBody
    @LogController
    public Map<String,String> editR(@RequestParam("params")String params){
        String strs[]=params.split(",");
        String id = strs[0];
        String name = strs[1];
        String pdesc = strs[2];
        Permission permission = new Permission();
        permission.setId(Integer.parseInt(id));
        permission.setName(name);
        permission.setPdesc(pdesc);

        Map<String,String> result = new HashMap<String,String>();
        try {
            permissionService.doSave(permission);
            result.put("result","success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result","error");
        }
        return result;
    }

}
