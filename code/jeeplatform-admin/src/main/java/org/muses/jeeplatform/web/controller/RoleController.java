package org.muses.jeeplatform.web.controller;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.muses.jeeplatform.core.Constants;
import org.muses.jeeplatform.model.entity.Role;
import org.muses.jeeplatform.model.entity.User;
import org.muses.jeeplatform.service.RoleService;
import org.muses.jeeplatform.utils.DateJsonValueProcessor;
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
import java.util.Date;

/**
 * Created by Nicky on 2017/7/30.
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{

    @Autowired
    RoleService roleService;

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

        JSONArray jsonArray = JSONArray.fromObject(rolePage.getContent());

        mv.addObject("roles",jsonArray.toString());
        mv.setViewName("admin/role/role_list");
        return mv;
    }


}
