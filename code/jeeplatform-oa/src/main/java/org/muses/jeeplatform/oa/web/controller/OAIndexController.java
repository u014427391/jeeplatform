package org.muses.jeeplatform.oa.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020年04月11日  修改内容:
 * </pre>
 */
@Controller
public class OAIndexController {

    @GetMapping(value = {"/"})
    public ModelAndView toIndexPage(){
        return new ModelAndView("index");
    }

}
