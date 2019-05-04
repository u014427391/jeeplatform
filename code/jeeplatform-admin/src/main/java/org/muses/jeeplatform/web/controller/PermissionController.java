package org.muses.jeeplatform.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.muses.jeeplatform.core.Constants;
import org.muses.jeeplatform.core.entity.admin.Permission;
import org.muses.jeeplatform.service.PermissionPageService;
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
    @RequestMapping(value = "/queryAll", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryAll(HttpServletRequest request, HttpServletResponse response, Model model){
        String pageIndexStr = request.getParameter("pageIndex");

        int pageSize = Constants.PAGE_SIZE;
        ModelAndView mv = this.getModelAndView();
        Page<Permission> permissionPage;

        if(pageIndexStr==null||"".equals(pageIndexStr)){
            pageIndexStr = "0";
        }

        int pageIndex = Integer.parseInt(pageIndexStr);

        permissionPage = permissionPageService.findAll(pageIndex+1, pageSize, Sort.Direction.ASC,"id");
        mv.addObject("totalCount",permissionPage.getTotalElements());
        mv.addObject("pageIndex",pageIndex);

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
    @RequestMapping(value = "/editP", method = RequestMethod.POST)
    @ResponseBody
    public void editR(@RequestParam("params")String params, HttpServletResponse response){
        String strs[]=params.split(",");
        String id = strs[0];
        String name = strs[1];
        String pdesc = strs[2];
        Permission permission = new Permission();
        permission.setId(Integer.parseInt(id));
        permission.setName(name);
        permission.setPdesc(pdesc);

        PrintWriter out = null;

        response.setCharacterEncoding("utf-8");

        JSONObject obj = new JSONObject();

        try {
            out = response.getWriter();
            permissionService.doSave(permission);
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
