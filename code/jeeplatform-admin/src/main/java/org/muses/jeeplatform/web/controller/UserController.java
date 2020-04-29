package org.muses.jeeplatform.web.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.muses.jeeplatform.core.CommonConsts;
import org.muses.jeeplatform.core.email.JavaEmailSender;
import org.muses.jeeplatform.core.entity.admin.Role;
import org.muses.jeeplatform.core.entity.admin.User;
import org.muses.jeeplatform.core.dto.admin.RoleVO;
import org.muses.jeeplatform.core.excel.ExcelViewWrite;
import org.muses.jeeplatform.service.RolePageService;
import org.muses.jeeplatform.service.RoleService;
import org.muses.jeeplatform.service.UserService;
import org.muses.jeeplatform.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * Created by Nicky on 2017/7/29.
 */
@Api(value="用户操作接口",tags={"用户操作接口"})
@RequestMapping("/user")
@Controller
public class UserController extends BaseController {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    RolePageService rolePageService;


    /**
     * 查询所有管理员信息并分页显示
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ApiOperation(value="用户信息列表", notes="用户信息列表")
    @RequestMapping(value = "/queryAll", produces = "application/json;charset=UTF-8")
    public ModelAndView findAll(HttpServletRequest request, HttpServletResponse response, Model model) {
        String pageIndexStr = request.getParameter("pageIndex");

        int pageSize = CommonConsts.PAGE_SIZE;
        ModelAndView mv = this.getModelAndView();
        Page<User> userPage;

        if (pageIndexStr == null || "".equals(pageIndexStr)) {
            pageIndexStr = "0";
        }

        int pageIndex = Integer.parseInt(pageIndexStr);

        userPage = userService.findAll(pageIndex + 1, pageSize, Sort.Direction.ASC, "id");
        mv.addObject("totalCount", userPage.getTotalElements());
        mv.addObject("pageIndex", pageIndex);
//        JsonConfig cfg = new JsonConfig();
//        cfg.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
//        JsonConfig jcg = new JsonConfig();
//        jcg.registerJsonValueProcessor(Date.class,
//                new DateJsonValueProcessor("yyyy-MM-dd"));
//        JSONArray jsonArray = JSONArray.fromObject(userPage.getContent(), jcg);
        //System.out.println(jsonArray.toString());
        String json = JSON.toJSONString(userPage.getContent());
        mv.addObject("users", json);
        mv.setViewName("admin/user/sys_user_list");
        return mv;
    }

    /**
     * 根据关键字和日期查询并分页显示
     *
     * @param pageIndexStr
     * @param keyword
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
    @RequestMapping(value = "/searchU", produces = "application/json;charset=UTf-8")
    public ModelAndView doSearch(@RequestParam(value = "pageIndex",required = true) String pageIndexStr, @RequestParam(value = "keyword",required = false) String keyword,
                                 @RequestParam(value = "startDate",required = false) String startDateStr, @RequestParam(value = "endDate",required = false) String endDateStr) {
        int pageSize = CommonConsts.PAGE_SIZE;
        ModelAndView mv = this.getModelAndView();
        Page<User> userPage;

        if (pageIndexStr == null || "".equals(pageIndexStr)) {
            pageIndexStr = "0";
        }

        int pageIndex = Integer.parseInt(pageIndexStr);

        System.out.println(startDateStr + " and " + endDateStr);

        Date startDate = DateUtils.parse("yyyy-MM-dd", startDateStr);
        Date endDate = DateUtils.parse("yyyy-MM-dd", endDateStr);
        userPage = userService.searchU(pageIndex + 1, pageSize, Sort.Direction.ASC, "id", keyword, startDate, endDate);

        for (User u : userPage.getContent()) {
            System.out.println(u.getUsername());
        }

        mv.addObject("totalCount", userPage.getTotalElements());
        mv.addObject("pageIndex", pageIndex);
//        JsonConfig cfg = new JsonConfig();
//        cfg.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
//        JsonConfig jcg = new JsonConfig();
//        jcg.registerJsonValueProcessor(Date.class,
//                new DateJsonValueProcessor("yyyy-MM-dd"));
//        JSONArray jsonArray = JSONArray.fromObject(userPage.getContent(), jcg);

        String json = JSON.toJSONString(userPage.getContent());
        mv.addObject("users", json);
        mv.setViewName("admin/user/sys_user_list");
        return mv;
    }

    /**
     * 跳转到新增管理员页面
     *
     * @return
     */
    @GetMapping(value = "/goAddU")
    public String goAddU() {
        return "admin/user/sys_user_add";
    }

    /**
     * 新增管理员
     *
     * @param params
     */
    @ApiOperation(value = "新增用户",notes = "新增用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="params",value = "json参数",paramType = "query",dataType = "String")
    })
    @PostMapping(value = "/addU")
    @ResponseBody
    public Map<String,String> addU(@RequestParam("params") String params) {
        String[] arrs = params.split(",");

        String username = arrs[0];
        String password = arrs[1];
        String phone = arrs[2];
        String sex = arrs[3];
        String email = arrs[4];
        String mark = arrs[5];

        String rank = "user";

        //Shiro框架SHA加密
        String passwordsha = new SimpleHash("SHA-1",username,password).toString();

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordsha);
        user.setPhone(phone);
        user.setSex(sex);
        user.setEmail(email);
        user.setMark(mark);
        user.setRank(rank);
        user.setRegTime(new Date());
        user.setLocked(false);
        user.setLoginIp("127.0.0.1");
        user.setLastLogin(new Date());

        Map<String,String> result = new HashMap<String,String>();

        try {
            userService.saveU(user);
            result.put("result", "success");
            result.put("status","200");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", "error");
        }
        return result;
    }

    @ApiOperation(value = "跳转到编辑用户信息页面",notes = "编辑用户信息页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",paramType = "query",dataType = "String")
    })
    @GetMapping(value = "/goEditU")
    public String goEditU(@RequestParam("userId")String userId, Model model) {
        User user = userService.findByUId(Integer.parseInt(userId));
        model.addAttribute("user",user);
        return "admin/user/sys_user_edit";
    }

    @PostMapping(value = "/editU")
    @ResponseBody
    public Map<String,String> editU(@RequestParam("params")String params) {
        String[] arrs = params.split(",");

        String userid = arrs[0];
        String username = arrs[1];
        String password = arrs[2];
        String phone = arrs[3];
        String sex = arrs[4];
        String email = arrs[5];
        String mark = arrs[6];
        String loginIp = arrs[7];
        String lastLogin = arrs[8];

        String rank = "user";

        User user = new User();
        user.setId(Integer.parseInt(userid));
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setSex(sex);
        user.setEmail(email);
        user.setMark(mark);
        user.setRank(rank);
        user.setRegTime(new Date());
        user.setLocked(false);
        user.setLoginIp(loginIp);
        user.setLastLogin(DateUtils.parse("yyyy-MM-dd",lastLogin));

        Map<String,String> result = new HashMap<String,String>();
        try {
            userService.saveU(user);
            result.put("result","success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", "error");
        }
        return result;
    }

    /**
     * 跳转到分配用户角色页面
     * @param userId
     * @param model
     * @return
     */
    @GetMapping(value = "/goAuthU")
    public String goAuthorise(@RequestParam("userId")String userId, Model model){

        User user = userService.findByUId(Integer.parseInt(userId));
        //System.out.println(user.getEmail());
        Set<Role> hasRoles = user.getRoles();

        List<Role> roleList = new ArrayList<Role>();
        for(Role r:hasRoles){
            roleList.add(r);
            System.out.println(r.getRoleName());
        }

        Iterable<Role> roles = rolePageService.findAllRole();

        List<RoleVO> rolevoes = new ArrayList<RoleVO>();

        for(Role r:roles){
            if(roleList.contains(r)){
                RoleVO vo = new RoleVO();
                vo.setRoleId(r.getRoleId());
                vo.setRoleName(r.getRoleName());
                vo.setRoleDesc(r.getRoleDesc());
                vo.setChecked(true);
                rolevoes.add(vo);
            }else{
                RoleVO vo = new RoleVO();
                vo.setRoleId(r.getRoleId());
                vo.setRoleName(r.getRoleName());
                vo.setRoleDesc(r.getRoleDesc());
                vo.setChecked(false);
                rolevoes.add(vo);
            }
        }

        model.addAttribute("roles",rolevoes);
        model.addAttribute("userId",userId);
        return "admin/user/sys_user_auth";
    }

    @ApiOperation(value="修改用户", notes="修改用户")
    @PostMapping(value = "/auth",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,String> doAuth(@RequestParam("params")String params ){

        String[] strs = params.split(";");
        String userId = strs[0];
        List<Integer> roleIds = new ArrayList<Integer>();
        if(strs.length>=2){
            if(strs[1].indexOf(",")!=-1){
                String[] ids = strs[1].split(",");
                for(int i =0;i<ids.length;i++){
                    roleIds.add(Integer.parseInt(ids[i]));
                }
            }else{
                roleIds.add(Integer.parseInt(strs[1]));
            }
        }

        Map<String,String> result = new HashMap<String,String>();
        try {
            User user = userService.findByUId(Integer.parseInt(userId));
            List<Role> roles = roleService.findAll(roleIds);
            Set<Role> preRoles = user.getRoles();
            for(Role r:roles){
                if(preRoles.contains(r))
                    continue;
                user.getRoles().add(r);
            }
            userService.saveU(user);
            result.put("result", "success");
        }catch (Exception e){
            e.printStackTrace();
            result.put("result", "error");
        }
        return result;
    }

    /**
     * 跳转到发送邮件页面
     *
     * @return
     */
    @GetMapping(value = "/goSendEmail")
    public ModelAndView goSendEmailPage(@RequestParam("toEmails") String toEmails) {
        ModelAndView mv = this.getModelAndView();
        mv.addObject("toEmails", toEmails);
        mv.setViewName("admin/common/send_email");
        return mv;
    }

    /**
     * 发送邮件
     *
     * @param toEmail
     * @param title
     * @param content
     * @param response
     * @throws Exception
     */
    @PostMapping(value = "/sendEmail", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String,String> sendEmail(@RequestParam("toEmail") String toEmail, @RequestParam("title") String title,
                          @RequestParam("content") String content, HttpServletResponse response) throws Exception {
        Map<String,String> result =new HashMap<String,String>();
        try {
            JavaEmailSender.sendEmail(toEmail, title, content);
            result.put("msg", "ok");
        }catch (Exception e){
            e.printStackTrace();
            log.error("异常:{}"+e);
            result.put("msg","error");
        }
        return result;
    }

    /**
     * 导出管理员信息到Excel表
     * @since 1.0.0
     */
    @GetMapping(value = "/exportExcel")
    public ModelAndView exportExcel(@RequestParam("ids") String idstr) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();

        titles.add("序号");
        titles.add("用户名");
        titles.add("描述");
        titles.add("手机");
        titles.add("邮箱");
        titles.add("最近登录");
        titles.add("上次登录IP");

        dataMap.put("titles", titles);

        List<HashMap<String, Object>> values = new ArrayList<HashMap<String, Object>>();
        String[] ids = idstr.split(",");
        for (int i = 0; i < ids.length; i++) {
            User user = userService.findByUId(Integer.parseInt(ids[i]));
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("var1", user.getId());
            map.put("var2", user.getUsername());
            map.put("var3", user.getMark());
            map.put("var4", user.getPhone());
            map.put("var5", user.getEmail());
            map.put("var6", user.getLastLogin());
            map.put("var7", user.getLoginIp());
            values.add(map);
        }

        dataMap.put("values", values);

        ExcelViewWrite ev = new ExcelViewWrite();

        ModelAndView mv = new ModelAndView(ev, dataMap);

        return mv;
    }

    /**
     * 跳转到修改密码页面
     * @return
     */
    @GetMapping(value = "/toUpdatePwd/{username}")
    public ModelAndView toUpdatePwd(@PathVariable("username")String username){
        ModelAndView mv = this.getModelAndView();
        mv.addObject("username",username);
        mv.setViewName("admin/frame/pwd_update");
        return mv;
    }

    /**
     * 修改密码接口，RESTFUL风格，给页面调用
     * @param username
     * @param password
     */
    @PostMapping(value = "/updatePwd/{username}/{password}")
    @ResponseBody
    public Map<String,String> updatePwd(@PathVariable("username")String username,@PathVariable("password")String password){
        Map<String,String> map = new HashMap<String,String>();
        password = new SimpleHash("SHA-1",username,password).toString();
        log.info("账号:{},密码:{}", username, password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        try{
            this.userService.updateU(user);
            map.put("result","success");
            return map;
        }catch (Exception e) {
            log.error("异常：{}"+e);
            map.put("result","error");
            return map;
        }
    }

}
