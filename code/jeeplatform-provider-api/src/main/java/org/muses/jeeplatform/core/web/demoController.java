package org.muses.jeeplatform.core.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author caiyuyu
 * @date 2017/10/26
 */
//@RestController
public class demoController {
    @RequestMapping("/")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView("base");
        return mv;
    }
}
