package org.muses.jeeplatform.web.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.collections.map.HashedMap;
import org.muses.jeeplatform.core.Constants;
import org.muses.jeeplatform.core.ExcelViewWrite;
import org.muses.jeeplatform.core.JavaEmailSender;
import org.muses.jeeplatform.model.entity.User;
import org.muses.jeeplatform.service.UserService;
import org.muses.jeeplatform.utils.DateJsonValueProcessor;
import org.muses.jeeplatform.utils.DateUtils;
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
import java.io.PrintWriter;
import java.util.*;


/**
 * Created by Nicky on 2017/7/29.
 */
@RequestMapping("/user")
@Controller
public class UserController extends BaseController{

    @Autowired
    UserService userService;

    /**
     * 查询所有管理员信息并分页显示
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/queryAll",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelAndView findAll(HttpServletRequest request, HttpServletResponse response, Model model){
        //当前页
        String pageIndexStr = request.getParameter("pageIndex");

        //每一页的页数
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

    /**
     * 根据关键字和日期查询并分页显示
     * @param pageIndexStr
     * @param keyword
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    @RequestMapping(value = "/searchU", produces = "application/json;charset=UTf-8")
    @ResponseBody
    public ModelAndView doSearch(@RequestParam("pageIndex")String pageIndexStr, @RequestParam("keyword")String keyword,
                                 @RequestParam("startDate")String startDateStr,@RequestParam("endDate")String endDateStr){
        int pageSize = Constants.PAGE_SIZE;
        ModelAndView mv = this.getModelAndView();
        Page<User> userPage;

        if(pageIndexStr==null||"".equals(pageIndexStr)){
            pageIndexStr = "0";
        }

        int pageIndex = Integer.parseInt(pageIndexStr);

        System.out.println(startDateStr+" and " +endDateStr);

        Date startDate = DateUtils.parse("yyyy-MM-dd",startDateStr);
        Date endDate = DateUtils.parse("yyyy-MM-dd", endDateStr);
        userPage = userService.searchU(pageIndex+1, pageSize, Sort.Direction.ASC,"id", keyword, startDate, endDate);

        for(User u:userPage.getContent()){
            System.out.println(u.getUsername());
        }

        mv.addObject("totalCount",userPage.getTotalElements());
        mv.addObject("pageIndex",pageIndex);
//        JsonConfig cfg = new JsonConfig();
//        cfg.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
        JsonConfig jcg = new JsonConfig();
        jcg.registerJsonValueProcessor(Date.class,
                new DateJsonValueProcessor("yyyy-mm-dd"));
        JSONArray jsonArray = JSONArray.fromObject(userPage.getContent(),jcg);

        mv.addObject("users",jsonArray.toString());
        mv.setViewName("admin/user/sys_user_list");
        return mv;
    }

    /**
     * 跳转到发送邮件页面
     * @return
     */
    @RequestMapping(value = "/goSendEmail")
    public ModelAndView goSendEmailPage(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("admin/common/send_email");
        return mv;
    }

    /**
     * 发送邮件
     * @param toEmail
     * @param title
     * @param content
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/sendEmail", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void sendEmail(@RequestParam("toEmail")String toEmail, @RequestParam("title")String title,
                            @RequestParam("content")String content, HttpServletResponse response) throws Exception {

        JavaEmailSender.sendEmail(toEmail, title, content);

        JSONObject obj = new JSONObject();
        obj.put("msg", "success");
        PrintWriter out;

        response.setCharacterEncoding("utf-8");
        out = response.getWriter();
        out.write(obj.toString());

        out.flush();
        out.close();

    }

    /**
     * 导出管理员信息到Excel表
     * @param idstr
     * @return
     */
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
