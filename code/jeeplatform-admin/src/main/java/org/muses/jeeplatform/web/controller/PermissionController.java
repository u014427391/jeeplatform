package org.muses.jeeplatform.web.controller;

import com.alibaba.fastjson.JSON;
import org.muses.jeeplatform.core.Constants;
import org.muses.jeeplatform.core.entity.admin.Permission;
import org.muses.jeeplatform.service.PermissionPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Nicky on 2017/12/3.
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    @Autowired
    PermissionPageService permissionPageService;

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


}
