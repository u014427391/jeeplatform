package org.muses.jeeplatform.web.controller;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.collections.map.HashedMap;
import org.muses.jeeplatform.core.Constants;
import org.muses.jeeplatform.core.ExcelViewWrite;
import org.muses.jeeplatform.model.entity.User;
import org.muses.jeeplatform.service.UserService;
import org.muses.jeeplatform.utils.DateJsonValueProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * Created by Nicky on 2017/7/29.
 */
@RequestMapping("/user")
@Controller
public class UserController extends BaseController{

    @Autowired
    UserService userService;

    @RequestMapping(value = "/queryAll",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelAndView findAll(HttpServletRequest request, HttpServletResponse response, Model model){
        String pageIndexStr = request.getParameter("pageIndex");

        int pageSize = Constants.PAGE_SIZE;
        ModelAndView mv = this.getModelAndView();
        Page<User> userPage;

        if(pageIndexStr==null||"".equals(pageIndexStr)){
            pageIndexStr = "0";
        }

        int pageIndex = Integer.parseInt(pageIndexStr);

        userPage = userService.findAll(pageIndex+1, pageSize, Sort.Direction.ASC,"id");
        mv.addObject("totalCount",userPage.getTotalElements());
        mv.addObject("pageIndex",pageIndex);
//        JsonConfig cfg = new JsonConfig();
//        cfg.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
        JsonConfig jcg = new JsonConfig();
        jcg.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor("yyyy-mm-dd"));
        JSONArray jsonArray = JSONArray.fromObject(userPage.getContent(),jcg);
        //System.out.println(jsonArray.toString());
        mv.addObject("users",jsonArray.toString());
        mv.setViewName("admin/user/sys_user_list");
        return mv;
    }

    @RequestMapping(value = "/exportExcel", produces = "application/json;charset=UTF-8")
    public ModelAndView exportExcel(@RequestParam("ids")String idstr){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        List<String> titles = new ArrayList<String>();

        titles.add("序号");
        titles.add("用户名");
        titles.add("描述");
        titles.add("手机");
        titles.add("邮箱");
        titles.add("最近登录");
        titles.add("上次登录IP");

        dataMap.put("titles", titles);

        List<HashMap<String,Object>> values = new ArrayList<HashMap<String,Object>>();
        String[] ids = idstr.split(",");
        for(int i =0;i<ids.length; i++){
            User user = userService.findByUId(Integer.parseInt(ids[i]));
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("var1",user.getId());
            map.put("var2",user.getUsername());
            map.put("var3",user.getMark());
            map.put("var4",user.getPhone());
            map.put("var5",user.getEmail());
            map.put("var6",user.getLastLogin());
            map.put("var7",user.getLoginIp());
            values.add(map);
        }

        dataMap.put("values",values);

        ExcelViewWrite ev = new ExcelViewWrite();

        ModelAndView mv = new ModelAndView(ev,dataMap);

        return mv;
    }


}
