package org.muses.jeeplatform.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/05/09 18:03  修改内容:
 * </pre>
 */
@RestController
public class IndexController {
    @Autowired
    private HelloController helloController;

    @GetMapping("/")
    public Object index(Authentication authentication) {
        return helloController.getCurrentUser(authentication);
    }
}
