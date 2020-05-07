package org.muses.jeeplatform.oauth.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/05/06 18:11  修改内容:
 * </pre>
 */
@Controller
public class LoginController {

    @GetMapping(value = {"/login"})
    public ModelAndView toLogin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = {"/index"})
    public ModelAndView toIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg","This is <b>great!</b>");
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
